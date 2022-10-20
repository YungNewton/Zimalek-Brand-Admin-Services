package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.exceptions.ForbiddenException;
import com.zmarket.brandadminservice.exceptions.NoContentException;
import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.UpdateBrandDto;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.repositories.BrandRepository;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import com.zmarket.brandadminservice.modules.category.repositories.BusinessCategoryRepository;
import com.zmarket.brandadminservice.modules.security.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class BrandServiceImplementation implements BrandService{
    @PersistenceContext
    private EntityManager entityManager;

    private final BrandRepository brandRepository;

    private final CurrentUser currentUser;

    private final BusinessVerificationFactory businessVerificationFactory;

    private final BusinessCategoryRepository businessCategoryRepository;

    @Override
    public Brand createBrand(BrandDto request) {
        Optional<Brand> optionalBrand = brandRepository.findByName(request.getName());
        if (optionalBrand.isPresent()){
            throw new BadRequestException("Brand with name already exists");
        }

        optionalBrand = brandRepository.findFirstByHandle(request.getHandle());
        if (optionalBrand.isPresent()){
            throw new BadRequestException("Brand with handle already exists");
        }

        Optional<BusinessCategory> optionalBusinessCategory = businessCategoryRepository.findById(request.getCategoryId());
        if (optionalBusinessCategory.isEmpty()){
            throw new NotFoundException("Category with Id doesn't exists");
        }

//        BusinessVerificationResponse verificationResponse;
//        verificationResponse = verifyBusiness(request.getBusinessNumber(), null, request.getName());

        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setCategory(optionalBusinessCategory.get());
        brand.setBusinessNumber(request.getBusinessNumber());
        brand.setBusinessType(request.getBusinessType());
        brand.setHandle(request.getHandle());
        brand.setAddress(request.getAddress());
        brand.setFacebookUrl(request.getFacebookUrl());
        brand.setTwitterUrl(request.getTwitterUrl());
        brand.setInstagramUrl(request.getInstagramUrl());
        brand.setLogo(request.getLogo());
        brand.setUserId(currentUser.getId());
        brand.setCreatedAt(new Date());
        brand = brandRepository.save(brand);
        return brand;
    }

    @Override
    public Brand getById(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException("Brand with id not found");
        }
        return optionalBrand.get();
    }

    @Override
    public BusinessVerificationResponse verifyBusiness(String number, String type ,String name) {
        BusinessVerificationService verificationService = businessVerificationFactory.resolve("dojah");
//        return verificationService.verify(number, BusinessType.valueOf(type), name);
        return new BusinessVerificationResponse(true, name, number);
    }

    @Override
    public Page<Brand> getAll(LocalDate startDate, LocalDate endDate, String category, Pageable pageable) {
        LocalDateTime start = Objects.nonNull(startDate) ? LocalDateTime.of(startDate, LocalTime.MIN) : null;
        LocalDateTime end = Objects.nonNull(startDate) ? LocalDateTime.of(endDate, LocalTime.MAX) : null;

        if (Objects.isNull(start) && Objects.isNull(end)  && Objects.isNull(category)) {
            Page<Brand> products = brandRepository.findAll(pageable);
            if(products.isEmpty()){
                throw new NoContentException();
            }

            return products;
        }

        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Brand> cq = qb.createQuery(Brand.class);

        Root<Brand> root = cq.from(Brand.class);

        List<Predicate> predicates = getPredicates(start, end, category, qb, root, cq);

        cq.select(root).where(predicates.toArray(new Predicate[]{}));

        List<Brand> res = entityManager.createQuery(cq).getResultList();

        if (res.isEmpty()) {
            throw new NoContentException();
        }

        return new PageImpl<>(res, pageable, res.size());
    }

    @Override
    public Brand getBrandByHandle(String handle) {
        Optional<Brand> optionalBrand = brandRepository.findFirstByHandle(handle);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException("Brand not found with handle");
        }
        return optionalBrand.get();
    }

    @Override
    public List<Brand> getAllAdminBrand() {
        List<Brand> brandList = brandRepository.findByUserId(currentUser.getId());
        if (brandList.isEmpty()) {
            throw new NoContentException();
        }
        return brandList;
    }

    @Override
    public Brand update(Long id, UpdateBrandDto dto) {

        Optional<Brand> optionalBrand = brandRepository.findByIdAndUserId(id, currentUser.getId());
        if (optionalBrand.isEmpty()){
            throw new ForbiddenException("you are authorized to view this resource");
        }

        Optional<BusinessCategory> optionalBusinessCategory = businessCategoryRepository.findById(dto.getCategoryId());
        if (optionalBusinessCategory.isEmpty()){
            throw new NotFoundException("Category with Id doesn't exists");
        }

        Brand brand = optionalBrand.get();
        brand.setCategory(optionalBusinessCategory.get());
        brand.setBusinessType(dto.getBusinessType());
        brand.setAddress(dto.getAddress());
        brand.setFacebookUrl(dto.getFacebookUrl());
        brand.setTwitterUrl(dto.getTwitterUrl());
        brand.setInstagramUrl(dto.getInstagramUrl());
        brand.setLogo(dto.getLogo());
        brand.setUpdatedAt(new Date());
        return brand;
    }

    private List<Predicate> getPredicates(LocalDateTime start, LocalDateTime end, String category, CriteriaBuilder qb, Root<Brand> root, CriteriaQuery<Brand> cq) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(start)) {
            predicates.add(qb.greaterThanOrEqualTo(root.get("createdAt"), start));
        }

        if (Objects.nonNull(end)) {
            predicates.add(qb.lessThan(root.get("createdAt"), end));
        }

        BusinessCategory businessCategory = businessCategoryRepository.findFirstByName(category).orElse(null);

        if (Objects.nonNull(businessCategory)) {
            predicates.add(qb.equal(root.get("category"), businessCategory));
        }


        return predicates;
    }

}

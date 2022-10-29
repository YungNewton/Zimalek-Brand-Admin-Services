package com.zmarket.brandadminservice.modules.brandrating.service.serviceimp;

import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.repositories.BrandRepository;
import com.zmarket.brandadminservice.modules.brandrating.dto.BrandRatingDto;
import com.zmarket.brandadminservice.modules.brandrating.model.BrandRating;
import com.zmarket.brandadminservice.modules.brandrating.repository.BrandRatingRepository;
import com.zmarket.brandadminservice.modules.brandrating.service.BrandRatingService;
import com.zmarket.brandadminservice.modules.security.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandRatingServiceImp implements BrandRatingService {

    private final BrandRatingRepository brandRatingRepository;
    private final BrandRepository brandRepository;
    private final CurrentUser currentUser;

    @Override
    public BrandRating rateBrand(Long id, BrandRatingDto request) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException("Brand with id not found");
        }
        Brand brand = optionalBrand.get();
        brand = updateBrandRatingInfo(request, brand);

        BrandRating brandRating = new BrandRating();
        brandRating.setStarCount(request.getStarCount());
        brandRating.setBrand(brand);
        brandRating.setComment(request.getComment());
        brandRating.setAnonymous(request.isAnonymous());
        brandRating.setUserId(currentUser.getId());
        brandRating.setUserFullName(currentUser.getFirstname()+" "+currentUser.getLastname());
        brandRating.setCreatedAt(new Date());

        brandRating = brandRatingRepository.save(brandRating);
        return brandRating;
    }

    private Brand updateBrandRatingInfo(BrandRatingDto request, Brand brand) {
        if (request.getStarCount()==1){
            brand.setTotalOneStarRating(brand.getTotalOneStarRating()+1);
        }
        if (request.getStarCount()==2){
            brand.setTotalTwoStarRating(brand.getTotalTwoStarRating()+2);
        }
        if (request.getStarCount()==3){
            brand.setTotalThreeStarRating(brand.getTotalThreeStarRating()+3);
        }
        if (request.getStarCount()==4){
            brand.setTotalFourStarRating(brand.getTotalFourStarRating()+4);
        }
        if (request.getStarCount()==5){
            brand.setTotalFiveStarRating(brand.getTotalFiveStarRating()+5);
        }

        int totalCount= brand.getTotalStarRating()+1;

        int average = (brand.getTotalOneStarRating()+ (brand.getTotalTwoStarRating()*2)+(brand.getTotalThreeStarRating()*3)+(brand.getTotalFourStarRating()*4)+ (brand.getTotalFiveStarRating()*5))/totalCount;

        brand.setTotalStarRating(totalCount);
        brand.setAvgStarRating(average);
        brand = brandRepository.save(brand);
        return brand;
    }

    @Override
    public List<BrandRating> getAllBrandRatingByBrandId(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException("Brand with id not found");
        }
        return brandRatingRepository.findByBrand(optionalBrand.get());
    }
}

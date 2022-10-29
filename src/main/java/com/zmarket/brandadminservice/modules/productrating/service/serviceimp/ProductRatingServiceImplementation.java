package com.zmarket.brandadminservice.modules.productrating.service.serviceimp;

import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.product.repositories.ProductRepository;
import com.zmarket.brandadminservice.modules.productrating.dto.ProductRatingDto;
import com.zmarket.brandadminservice.modules.productrating.model.ProductRating;
import com.zmarket.brandadminservice.modules.productrating.repository.ProductRatingRepository;
import com.zmarket.brandadminservice.modules.productrating.service.ProductRatingService;
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
public class ProductRatingServiceImplementation implements ProductRatingService {
    private final ProductRatingRepository productRatingRepository;
    private final ProductRepository productRepository;

    private final CurrentUser currentUser;


    @Override
    public ProductRating rateProduct(Long id, ProductRatingDto request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product with id not found");
        }
        Product product = optionalProduct.get();
        product = updateProductRatingInfo(request, product);

        ProductRating productRating = new ProductRating();
        productRating.setStarCount(request.getStarCount());
        productRating.setProduct(product);
        productRating.setComment(request.getComment());
        productRating.setAnonymous(request.isAnonymous());
        productRating.setUserId(currentUser.getId());
        productRating.setUserFullName(currentUser.getFirstname()+" "+currentUser.getLastname());
        productRating.setCreatedAt(new Date());

        productRating = productRatingRepository.save(productRating);
        return productRating;
    }

    private Product updateProductRatingInfo(ProductRatingDto request, Product product) {
        if (request.getStarCount()==1){
            product.setTotalOneStarRating(product.getTotalOneStarRating()+1);
        }
        if (request.getStarCount()==2){
            product.setTotalTwoStarRating(product.getTotalTwoStarRating()+2);
        }
        if (request.getStarCount()==3){
            product.setTotalThreeStarRating(product.getTotalThreeStarRating()+3);
        }
        if (request.getStarCount()==4){
            product.setTotalFourStarRating(product.getTotalFourStarRating()+4);
        }
        if (request.getStarCount()==5){
            product.setTotalFiveStarRating(product.getTotalFiveStarRating()+5);
        }

        int totalCount= product.getTotalStarRating()+1;

        int average = (product.getTotalOneStarRating()+ (product.getTotalTwoStarRating()*2)+(product.getTotalThreeStarRating()*3)+(product.getTotalFourStarRating()*4)+ (product.getTotalFiveStarRating()*5))/totalCount;

        product.setTotalStarRating(totalCount);
        product.setAvgStarRating(average);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public List<ProductRating> getAllProductRatingByProductId(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Product with id not found");
        }
        return productRatingRepository.findByProduct(optionalProduct.get());
    }
}

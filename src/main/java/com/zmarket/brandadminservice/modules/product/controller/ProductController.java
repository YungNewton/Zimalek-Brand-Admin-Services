package com.zmarket.brandadminservice.modules.product.controller;

import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.product.service.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@WrapApiResponse
@RequiredArgsConstructor
public class ProductController {
    private final ProductServices productServices;

    @PostMapping
    public Product create(@RequestBody @Valid ProductDto productDto ){
        return productServices.createNewProduct(productDto);
    }
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return productServices.getById(id);
    }
    @GetMapping
    public List<Product> getAll(){
        return productServices.getAll();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        productServices.delete(id);
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Long id,@Valid @RequestBody ProductDto productDto) {
        return productServices.update(id,productDto);
    }
}

package com.zmarket.brandadminservice.modules.brand.controllers;


import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/brand")
@WrapApiResponse
@RequiredArgsConstructor
public class BrandController {

    @GetMapping
    public Object get() {
        return Map.of("hello", "hi");
    }

    @GetMapping("/protected")
    public Object getProtected() {
        return Map.of("protected", "hi");
    }
}

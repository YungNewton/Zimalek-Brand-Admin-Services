package com.zmarket.brandadminservice.modules.brand.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VerifyMeBusinessData {
    private long rcNumber;
    private String companyName;
    private String companyType;
    private Date registrationDate;
    private String branchAddress;
    private String companyEmail;
    private String city;
    private String classification;
    private String headOfficeAddress;
    private String lga;
    private int affiliates;
    private String shareCapital;
    private String shareCapitalInWords;
    private String state;
    private String status;
}

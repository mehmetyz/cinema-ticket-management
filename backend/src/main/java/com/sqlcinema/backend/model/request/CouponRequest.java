package com.sqlcinema.backend.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequest {
    private String code;
    private float price;

}

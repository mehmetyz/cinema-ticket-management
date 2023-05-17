package com.sqlcinema.backend.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CouponResponse {
    private float before_price;
    private float after_price;
    private String coupon_code;

}

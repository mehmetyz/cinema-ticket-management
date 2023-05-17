package com.sqlcinema.backend.model;

import lombok.Data;

@Data
public class AmountCoupon extends Coupon{
    private float amount;
    private float min_price;

}

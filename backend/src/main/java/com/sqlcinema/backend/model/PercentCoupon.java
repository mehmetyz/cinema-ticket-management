package com.sqlcinema.backend.model;

import lombok.Data;

@Data
public class PercentCoupon extends Coupon{
    private float rate;
    private float up_to;
}

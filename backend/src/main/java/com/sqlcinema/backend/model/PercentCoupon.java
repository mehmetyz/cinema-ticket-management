package com.sqlcinema.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PercentCoupon extends Coupon{
    private float rate;
    private float upTo;
}

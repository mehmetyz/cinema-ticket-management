package com.sqlcinema.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AmountCoupon extends Coupon{
    private float amount;
    private float minPrice;
}

package com.sqlcinema.backend.model.request;

import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.CouponType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CouponRequest extends Coupon {
    private CouponType type;
    
    private float discount;
    private float limit;
}

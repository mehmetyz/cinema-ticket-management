package com.sqlcinema.backend.service;
import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.CouponType;

import java.util.List;

public interface CouponService {
    List<? extends Coupon> getCoupons(CouponType type);

    Coupon getCouponByCode(String code);
}

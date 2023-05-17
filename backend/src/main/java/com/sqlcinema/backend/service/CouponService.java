package com.sqlcinema.backend.service;
import com.sqlcinema.backend.model.AmountCoupon;
import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.PercentCoupon;
import com.sqlcinema.backend.model.order.CouponOrder;
import com.sqlcinema.backend.model.request.CouponRequest;
import com.sqlcinema.backend.model.response.CouponResponse;

import java.util.List;

public interface CouponService {
    List<Coupon> getCoupon(CouponOrder orderBy);
    List<PercentCoupon> getPercentCoupon(CouponOrder orderBy);
    List<AmountCoupon> getAmountCoupon(CouponOrder orderBy);

    Coupon getCouponById(String code);
    void useCoupon(String code);
}

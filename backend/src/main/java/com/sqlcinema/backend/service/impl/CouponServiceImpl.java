package com.sqlcinema.backend.service.impl;

import com.sqlcinema.backend.model.AmountCoupon;
import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.PercentCoupon;
import com.sqlcinema.backend.model.order.CouponOrder;
import com.sqlcinema.backend.model.request.CouponRequest;
import com.sqlcinema.backend.model.response.CouponResponse;
import com.sqlcinema.backend.repository.CouponRepository;
import com.sqlcinema.backend.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;
    public List<Coupon> getCoupon(CouponOrder orderBy){
        return couponRepository.getCoupon(orderBy);
    }
    public List<PercentCoupon> getPercentCoupon(CouponOrder orderBy){
        return couponRepository.getPercentCoupon(orderBy);
    }
    public List<AmountCoupon> getAmountCoupon(CouponOrder orderBy){
        return couponRepository.getAmountCoupon(orderBy);
    }
    public Coupon getCouponById(String code){
        return couponRepository.getCouponById(code);
    }
    public void useCoupon(String code){
        couponRepository.useCoupon(code);
    }
}

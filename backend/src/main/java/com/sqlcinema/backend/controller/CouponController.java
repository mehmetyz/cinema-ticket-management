package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.AmountCoupon;
import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.PercentCoupon;
import com.sqlcinema.backend.model.order.CouponOrder;
import com.sqlcinema.backend.model.request.CouponRequest;
import com.sqlcinema.backend.model.response.CouponResponse;
import com.sqlcinema.backend.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/coupon")
@AllArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Coupon>> getCoupon() {
        return ok(couponService.getCoupon(CouponOrder.EXPIREDATE));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/amount")
    public ResponseEntity<List<AmountCoupon>> getAmountCoupon() {
        return ok(couponService.getAmountCoupon(CouponOrder.EXPIREDATE));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/percent")
    public ResponseEntity<List<PercentCoupon>> getPercentCoupon() {
        return ok(couponService.getPercentCoupon(CouponOrder.EXPIREDATE));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/byID")
    public ResponseEntity<Coupon> getCouponById(@RequestBody CouponRequest couponRequest){
        return ok(couponService.getCouponById(couponRequest.getCode()));
    }
    @PreAuthorize("hasAnyAuthority('USER', 'USER_MANAGER', 'ADMIN')")
    @PostMapping("/useCoupon")
    public ResponseEntity<?> useCoupon(@RequestBody CouponRequest couponRequest){
        Coupon coupon=couponService.getCouponById(couponRequest.getCode());
        if(coupon.getCode()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid coupon code");
        }
        else if(coupon.getCoupon_left()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no coupon left");
        }
        else if(coupon.getActivate_date().compareTo(new Date())>0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The activation didn't happened");
        }
        else if(coupon.getExpire_date().compareTo(new Date())<0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The coupon expired");
        }
        try{
            AmountCoupon amountCoupon=(AmountCoupon) coupon;
            if(couponRequest.getPrice()<amountCoupon.getMin_price()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The min price didn't reached, the min price is : "+amountCoupon.getMin_price());
            }
            else{
                CouponResponse couponResponse= CouponResponse.builder().coupon_code(couponRequest.getCode()).
                                                                        before_price(couponRequest.getPrice()).
                                                                        after_price(Float.max(0,couponRequest.getPrice()-amountCoupon.getAmount())).build();
                return ok(couponResponse);
            }
        }
        catch (ClassCastException E){
            PercentCoupon percentCoupon=(PercentCoupon) coupon;
            CouponResponse couponResponse= CouponResponse.builder().coupon_code(couponRequest.getCode()).
                    before_price(couponRequest.getPrice()).
                    after_price(Float.max(0,couponRequest.getPrice()-Float.min(couponRequest.getPrice()*percentCoupon.getRate()/100,percentCoupon.getUp_to()))).build();
            return ok(couponResponse);
        }
    }
}

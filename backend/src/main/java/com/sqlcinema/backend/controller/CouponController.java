package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.CouponType;
import com.sqlcinema.backend.model.request.CouponRequest;
import com.sqlcinema.backend.service.CouponService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/coupon")
@AllArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<? extends Coupon>> getCoupon(
            @RequestParam(name = "type", defaultValue = "ALL") CouponType type) {
        return ok(couponService.getCoupons(type));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable("code") String code) {
        Coupon existing = couponService.getCouponByCode(code);
        if (existing == null) {
            return notFound().build();
        }

        return ok(existing);
    }
    
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Coupon> createCoupon(@RequestBody CouponRequest couponRequest) {
        return ok(couponService.createCoupon(couponRequest));
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{code}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable("code") String code, @RequestBody CouponRequest couponRequest) {
        Coupon existing = couponService.getCouponByCode(code);
        if (existing == null) {
            return notFound().build();
        }
        return ok(couponService.updateCoupon(code, couponRequest));
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable("code") String code) {
        Coupon existing = couponService.getCouponByCode(code);
        if (existing == null) {
            return notFound().build();
        }
        couponService.deleteCoupon(code);
        return ok().build();
    }
}

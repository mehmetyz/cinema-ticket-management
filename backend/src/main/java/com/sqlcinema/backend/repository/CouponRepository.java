package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.model.AmountCoupon;
import com.sqlcinema.backend.model.Coupon;
import com.sqlcinema.backend.model.Genre;
import com.sqlcinema.backend.model.PercentCoupon;
import com.sqlcinema.backend.model.order.CouponOrder;
import com.sqlcinema.backend.model.request.CouponRequest;
import com.sqlcinema.backend.model.response.CouponResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.sqlcinema.backend.common.Constants.createObjectArray;
import static com.sqlcinema.backend.common.Constants.createStringArray;

@Repository
@AllArgsConstructor
public class CouponRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomLogger logger;

    public List<PercentCoupon> getPercentCoupon(CouponOrder orderBy) {
        String percent_query = "SELECT * FROM Coupon NATURAL INNER JOIN PercentCoupon"+
                (orderBy != null && !orderBy.getValue().isEmpty() ? " ORDER BY " + orderBy.getValue() : "");
        logger.sqlLog(percent_query, "SELECT", createStringArray("PercentCoupon"), createObjectArray());
        return jdbcTemplate.query(percent_query, BeanPropertyRowMapper.newInstance(PercentCoupon.class));
    }
    public List<AmountCoupon> getAmountCoupon(CouponOrder orderBy) {
        String amount_query = "SELECT * FROM Coupon NATURAL INNER JOIN AmountCoupon"+
                (orderBy != null && !orderBy.getValue().isEmpty() ? " ORDER BY " + orderBy.getValue() : "");
        logger.sqlLog(amount_query, "SELECT", createStringArray("AmountCoupon"), createObjectArray());
        return jdbcTemplate.query(amount_query, BeanPropertyRowMapper.newInstance(AmountCoupon.class));
    }
    public void useCoupon(String code){
        String query="UPDATE Coupon SET Coupon.coupon_left=Coupon.coupon_left-1 WHERE Coupon.code=?";
        jdbcTemplate.update(query,code);
        logger.sqlLog(query, "UPDATE", createStringArray("COUPON"), createObjectArray());
    }
    public List<Coupon> getCoupon(CouponOrder orderBy) {
        List<Coupon> coupons=new ArrayList<>();
        coupons.addAll(getAmountCoupon(orderBy));
        coupons.addAll(getPercentCoupon(orderBy));
        return coupons;
    }
    public Coupon getCouponById(String code){
        String percent_query = "SELECT * FROM (SELECT * FROM Coupon WHERE Coupon.code = ? ) as Coupon NATURAL INNER JOIN PercentCoupon ";
        String amount_query = "SELECT * FROM (SELECT * FROM Coupon WHERE Coupon.code = ? ) as Coupon NATURAL INNER JOIN AmountCoupon ";
        List<AmountCoupon> amountCoupon=jdbcTemplate.query(amount_query,new Object[]{code}, BeanPropertyRowMapper.newInstance(AmountCoupon.class));
        if(amountCoupon.isEmpty()){
            List<PercentCoupon> percentCoupon=jdbcTemplate.query(percent_query, new Object[]{code},BeanPropertyRowMapper.newInstance(PercentCoupon.class));
            if(percentCoupon.isEmpty()){
                return new Coupon();
            }
            logger.sqlLog(percent_query, "SELECT", createStringArray("PercentCoupon"), createObjectArray());
            return percentCoupon.get(0);
        }
        logger.sqlLog(amount_query, "SELECT", createStringArray("Coupon"), createObjectArray());
        return amountCoupon.get(0);
    }
}

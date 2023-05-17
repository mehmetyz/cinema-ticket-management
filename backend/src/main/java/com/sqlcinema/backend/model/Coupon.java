package com.sqlcinema.backend.model;
import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    private String code;
    private int coupon_left;
    private Date expire_date;
    private Date activate_date;

}

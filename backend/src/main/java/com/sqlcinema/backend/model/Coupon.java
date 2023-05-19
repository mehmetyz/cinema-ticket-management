package com.sqlcinema.backend.model;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class Coupon {
    private String code;
    private int couponLeft;
    private Date expireDate;
    private Date activateDate;
}

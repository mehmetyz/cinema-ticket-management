package com.sqlcinema.backend.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class Coupon {
    private String code;
    private int couponLeft;
    private Date expireDate;
    private Date activateDate;
}

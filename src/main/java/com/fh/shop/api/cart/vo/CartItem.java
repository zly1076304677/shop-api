package com.fh.shop.api.cart.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartItem implements Serializable {
    private Long goodsId;

    private String goodsName;

    private BigDecimal price;

    private int num;

    private BigDecimal subPrice;

    private String imageUrl;
}

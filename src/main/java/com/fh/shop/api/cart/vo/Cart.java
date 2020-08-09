package com.fh.shop.api.cart.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    private BigDecimal totalPrice;

    private int totalNum;

    private List<CartItem> cartItems = new ArrayList<>();

}

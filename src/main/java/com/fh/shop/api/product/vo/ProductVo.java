package com.fh.shop.api.product.vo;

import lombok.Data;

@Data
public class ProductVo {
    private Long id;
    private String productName;
    private String price;
    private String image;
}

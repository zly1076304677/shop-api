package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartItem;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.product.mapper.ProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse addItem(Long memberId, Long goodsId, int num) {
        //判断商品是否存在
        Product product = productMapper.selectById(goodsId);
        if(null == product){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_NULL);
        }
        //判断商品状态是否正常
        if(product.getStatus() == SystemConstant.PRODUCT_IS_DOWN){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_DOWN);
        }
        //如果会员已经有了对应的购物车

        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        if (StringUtils.isEmpty(cartJson)){
            //直接向购物车放入商品
            Cart cart = JSONObject.parseObject(cartJson, Cart.class);
            List<CartItem> cartItemList = cart.getCartItems();
            CartItem cartItem = null;
            for (CartItem item : cartItemList) {
                if (item.getGoodsId().longValue() == goodsId.longValue()){
                    cartItem = item;
                    break;
                }
            }
            if (cartItem != null){
                //如果商品存在更新商品的数量和小计，更新购物车
                cartItem.setNum(cartItem.getNum()+num);
                int num1 = cartItem.getNum();
                if(num1 <= 0){
                    //删除整个商品,从cartItemList里移除商品
                    //删除要倒着删除或者通过iterator删除
                    cartItemList.remove(cartItem);

                }else {
                    BigDecimal subPrice = BigDecimalUtil.mul(num1 + "", cartItem.getPrice().toString());
                    cartItem.setSubPrice(subPrice);

                }

                updateCart(memberId,cart);



            }else {
                //如果商品不存在添加商品，更新购物车
                if (num <= 0){
                    return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_NO);
                }
                //构建商品
                CartItem cartItemInfo = buildCartItem(num, product, cart);
                //加入购物车
                cart.getCartItems().add(cartItemInfo);
                updateCart(memberId,cart);
            }


        }else {

        }



        //如果会员没有对应的购物车
        //创建购物车
        Cart cart = new Cart();
        //构建商品
        CartItem cartItemInfo = buildCartItem(num, product, cart);
        //加入购物车
        cart.getCartItems().add(cartItemInfo);
        //更新购物车
        updateCart(memberId,cart);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse findItemList(Long memberId) {
        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        return ServerResponse.success(cart);
    }

    private CartItem buildCartItem(int num, Product product, Cart cart) {
        CartItem cartItemInfo = new CartItem();
        cartItemInfo.setGoodsId(product.getId());
        cartItemInfo.setSubPrice(product.getPrice());
        cartItemInfo.setImageUrl(product.getImage());
        cartItemInfo.setGoodsName(product.getProductName());
        cartItemInfo.setNum(num);
        BigDecimal subPrice = BigDecimalUtil.mul(num + "", product.getPrice().toString());
        cartItemInfo.setSubPrice(subPrice);
        return cartItemInfo;
    }

    private void updateCart(Long memberId, Cart cart) {
        List<CartItem> cartItemList = cart.getCartItems();
        int totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        String cartKey = KeyUtil.buildCartKey(memberId);
        if (cartItemList.size() ==0){
            //删除真个购物车
            RedisUtil.delete(cartKey);
            return;

        }
        //更新购物车
        for (CartItem item : cartItemList) {
            totalCount += item.getNum();
            totalPrice = BigDecimalUtil.add(totalPrice.toString(),item.getSubPrice().toString());
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalNum(totalCount);
        //最终往redis里更新
        String cartNewJson = JSONObject.toJSONString(cart);

        RedisUtil.set(cartKey,cartNewJson);
    }
}

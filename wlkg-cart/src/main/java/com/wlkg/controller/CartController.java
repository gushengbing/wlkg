package com.wlkg.controller;

import com.wlkg.pojo.Cart;
import com.wlkg.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        this.cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }
    /**
     * 查询购物车列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Cart>> queryCartList() {
        List<Cart> carts = this.cartService.queryCartList();
        if (carts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(carts);
    }
    /**
     * 修改购物车列表
     *
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestParam("skuId") Long skuId,
                                          @RequestParam("num") Integer num) {
        this.cartService.updateNum(skuId, num);
        return ResponseEntity.ok().build();
    }
    /**
     * 删除购物车列表
     *
     * @return
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId) {
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }


}


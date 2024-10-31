package com.wlkg.controller;

import com.wlkg.client.BrandClient;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.pojo.Brand;
import com.wlkg.pojo.Goods;
import com.wlkg.pojo.SearchRequest;
import com.wlkg.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    @Autowired
    private BrandClient brandClient;
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/brand/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand = brandClient.queryBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(goodsService.search(request));
    }

}

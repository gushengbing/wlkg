package com.wlkg.controller;

import com.wlkg.common.pojo.PageResult;
import com.wlkg.pojo.Sku;
import com.wlkg.pojo.Spu;
import com.wlkg.pojo.SpuDetail;
import com.wlkg.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private GoodsService service;

    /**
     * 分页查询squ对象
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key) {
        // 分页查询spu信息
        PageResult<Spu> result = service.querySpuByPageAndSort(page, rows, saleable, key);
        System.out.println("返回页面的数据："+result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/goods")
    public ResponseEntity<Void> insert(@RequestBody Spu spu){

        service.insertGoods(spu);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu) {
        service.update(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable(value = "id")Long id){
        SpuDetail spuDetail=service.querySpuDetailById(id);
        if (spuDetail==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spuDetail);
    }
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id) {
        List<Sku> skus = service.querySkuBySpuId(id);
        if (skus == null || skus.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(skus);
    }

    @DeleteMapping("/goods")
    public ResponseEntity<Void> deleteGoods(@RequestParam("id")Long id){
        System.out.println("删除商品传过来的ID:"+id);
        service.deleteGoods(id);

       return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/goods")
    public ResponseEntity<Void> updateGoods(@RequestParam("id")Long id,@RequestParam("saleable")Boolean saleable){
        System.out.println("修改上下架传过来的ID:"+id);
        System.out.println("修改上下架传过来的ID:"+saleable);
        service.updateGoods(id,saleable);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spuId查询Spu对象
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public  ResponseEntity<Spu> querySpuById(@PathVariable("id")Long id){
        Spu spu = service.querySkuBySId(id);
        return ResponseEntity.ok(spu);
    }

    @GetMapping("sku/{id}")
    public Sku querySkuById(@PathVariable("id")Long id){
        Sku sku = service.querySkuById(id);
        return ResponseEntity.ok(sku).getBody();
    }
}

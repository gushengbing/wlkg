package com.wlkg.controller;

import com.wlkg.common.pojo.PageResult;
import com.wlkg.pojo.Brand;
import com.wlkg.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brand/page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> result = brandService.queryBrandByPageAndSort(page,rows,sortBy,desc, key);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/brand")
    public ResponseEntity<Void> add(Brand brand , @RequestParam("cids")List<Long> cids){
        System.out.println("添加品牌对象:"+brand);
        System.out.println("添加品牌类型id:"+cids);
        brandService.addBrand(brand,cids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/brand")
    public ResponseEntity<Void> update(Brand brand , @RequestParam("cids")List<Long> cids){
        System.out.println("修改品牌对象:"+brand);
        System.out.println("修改品牌类型id:"+cids);
        brandService.updateBrand(brand,cids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/brand")
    public ResponseEntity<Void> delete(@RequestParam("id")Long id){
        System.out.println("删除的id:"+id);
        brandService.deleteBrand(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/brand/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable(name = "cid",required = false)Long cid){
        List<Brand>list=brandService.queryBrandByCid(cid);
        return ResponseEntity.ok(list);
    }

    /**
     *根据id查询品牌
     */
    @GetMapping("/brand/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.selectBrandById(id));
    }
}



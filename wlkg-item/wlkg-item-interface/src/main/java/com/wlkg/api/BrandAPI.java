package com.wlkg.api;

import com.wlkg.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BrandAPI {
    /**
     *根据id查询品牌
     */
    @GetMapping("/brand/{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);
}

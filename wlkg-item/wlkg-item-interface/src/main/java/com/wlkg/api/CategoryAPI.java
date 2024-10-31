package com.wlkg.api;

import com.wlkg.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryAPI {
    @GetMapping("/category/list/ids")
    public List<Category> queryCategoryByIds(@RequestParam("pid")List<Long> ids);
}

package com.wlkg.controller;

import com.wlkg.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class GoodsController {
    @Autowired
    private PageService service;

    /**
     * 根据spuId跳转到详情页面
     *
     * @param id
     * @return
     */
    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model) {
        // 根据spuId查询商品详情信息
        Map<String, Object> attributes = service.loadModel(id);
        model.addAllAttributes(attributes);
        //判断是否需要生成新的页面
        if (!service.exists(id)){
            service.syncCreateHtml(id);
        }

        return "item";
    }
    @GetMapping("/item2/{id}.html")
    @ResponseBody
    public  Map<String, Object> toItemPage(@PathVariable("id") Long id) {
        // 根据spuId查询商品详情信息
        Map<String, Object> attributes = service.loadModel(id);

        return attributes;
    }

}

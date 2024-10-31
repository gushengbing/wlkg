package com.wlkg.api;

import com.wlkg.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsAPI{

}

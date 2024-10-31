package com.wlkg.api;

import com.wlkg.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsAPI{
    Sku querySkuById(Long skuId);
}

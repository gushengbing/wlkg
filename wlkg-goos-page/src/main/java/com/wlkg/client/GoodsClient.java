package com.wlkg.client;

import com.wlkg.api.BrandAPI;
import com.wlkg.api.GoodsAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsAPI {
}

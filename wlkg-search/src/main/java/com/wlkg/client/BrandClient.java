package com.wlkg.client;

import com.wlkg.api.BrandAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface BrandClient extends BrandAPI {
}

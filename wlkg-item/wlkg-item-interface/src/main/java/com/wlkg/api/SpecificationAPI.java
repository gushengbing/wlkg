package com.wlkg.api;

import com.wlkg.pojo.SpecGroup;
import com.wlkg.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecificationAPI {
    @GetMapping("/spec/params")
    public List<SpecParam> querySpecParam(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic);
    // 查询规格参数组，及组内参数
    @GetMapping("/spec/groups/{cid}")
    public List<SpecGroup> querySpecGroup(@PathVariable(value = "cid")Long cid);

}

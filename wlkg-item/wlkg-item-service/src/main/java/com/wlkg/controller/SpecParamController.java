/*
package com.wlkg.controller;

import com.wlkg.pojo.SpecParam;
import com.wlkg.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpecParamController {
    @Autowired
    private SpecParamService specParamService;

    @GetMapping("/spec/params")
    public ResponseEntity<List<SpecParam>> querySpecParam(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic){

        List<SpecParam> list=specParamService.querySpecParam(gid,cid,searching,generic);

        return  ResponseEntity.ok(list);
    }

    @PostMapping("/spec/param")
    public ResponseEntity<List<Void>> insertSpecParam(@RequestBody SpecParam specParam){
        System.out.println("添加参数对象:"+specParam);
        specParamService.insertSpecParam(specParam);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/spec/param")
    public ResponseEntity<List<Void>> updateSpecParam(@RequestBody SpecParam specParam){
        System.out.println("修改参数对象:"+specParam);
        specParamService.updateSpecParam(specParam);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/spec/param/{id}")
    public ResponseEntity<List<Void>> deleteSpecParam(@PathVariable("id")Long id){
        System.out.println("删除参数对象Id:"+id);
        specParamService.deletepecParam(id);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
*/

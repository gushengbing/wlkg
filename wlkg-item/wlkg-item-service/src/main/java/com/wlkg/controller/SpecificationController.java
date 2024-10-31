package com.wlkg.controller;

import com.wlkg.pojo.SpecGroup;
import com.wlkg.pojo.SpecParam;
import com.wlkg.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询分类组
     * @param cid
     * @return
     */
    @GetMapping("/spec/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroup(@PathVariable(value = "cid")Long cid){
      //  System.out.println(cid);
        List<SpecGroup> list=specificationService.querySpecsByCid(cid);
      //  System.out.println(list);
        return  ResponseEntity.ok(list);
    }
    @PostMapping("/spec/group")
    public ResponseEntity<List<SpecGroup>> insertSpecGroup(@RequestBody SpecGroup specGroup){

        System.out.println("添加分组"+specGroup);
        specificationService.insertSpecGroup(specGroup);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/spec/group")
    public ResponseEntity<List<SpecGroup>> updateSpecGroup(@RequestBody SpecGroup specGroup){

        System.out.println("添加分组"+specGroup);
        specificationService.updateSpecGroup(specGroup);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/spec/group/{id}")
    public ResponseEntity<List<SpecGroup>> updateSpecGroup(@PathVariable("id")Long id){
        System.out.println("删除传过来的id:"+id);
        specificationService.deleteSpecGroup(id);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/spec/params")
    public ResponseEntity<List<SpecParam>> querySpecParam(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic){

        List<SpecParam> list=specificationService.querySpecParam(gid,cid,searching,generic);

        return  ResponseEntity.ok(list);
    }

    @PostMapping("/spec/param")
    public ResponseEntity<List<Void>> insertSpecParam(@RequestBody SpecParam specParam){
        System.out.println("添加参数对象:"+specParam);
        specificationService.insertSpecParam(specParam);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/spec/param")
    public ResponseEntity<List<Void>> updateSpecParam(@RequestBody SpecParam specParam){
        System.out.println("修改参数对象:"+specParam);
        specificationService.updateSpecParam(specParam);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/spec/param/{id}")
    public ResponseEntity<List<Void>> deleteSpecParam(@PathVariable("id")Long id){
        System.out.println("删除参数对象Id:"+id);
        specificationService.deletepecParam(id);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

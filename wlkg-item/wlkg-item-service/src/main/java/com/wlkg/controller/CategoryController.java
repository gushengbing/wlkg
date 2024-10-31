package com.wlkg.controller;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.enums.SucceedEnums;
import com.wlkg.pojo.Category;
import com.wlkg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 根据id查询分类集合
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        List<Category> list = categoryService.queryCategoryByPid(pid);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add")
    public  ResponseEntity<SucceedEnums>  add(@RequestBody Category category){
        System.out.println("分类添加从网页传过来的对象是:"+category);
        int i=categoryService.addCategory(category);
        if (i>0){

            return ResponseEntity.ok(SucceedEnums.ADD_OK);
        }
        return ResponseEntity.ok(SucceedEnums.ADD_FAIL);
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<SucceedEnums> del(@RequestBody Category category){
        System.out.println("分类删除传过来的id:"+category);
      categoryService.deleteCategory(category.getId());
        return ResponseEntity.ok(SucceedEnums.DEL_OK);
    }
    @PutMapping("/update")
    public  ResponseEntity<SucceedEnums> put(@RequestBody Category category){
        System.out.println("分类修改传过来的id:"+category);
        categoryService.updateCategory(category);
        return ResponseEntity.ok(SucceedEnums.UPDATE_OK);
    }


    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid) {
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据id查询商品分类
     * @return
     */
    @GetMapping("/list/ids")
    public  ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("pid")List<Long> ids){

        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }











}


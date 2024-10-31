package com.wlkg.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.mapper.BrandMapper;
import com.wlkg.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(pageInfo)){
            throw new WlkgException(ExceptionEnums.BRAND_IS_NOT);
        }
        // 返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }

    /**
     * 添加品牌数据
     * @param brand
     * @param cids
     */
    @Transactional //事务注解
    public void addBrand(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);

        for(Long cid :cids){
            brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }

    /**
     * 根据对象和id修改品牌数据
     * @param brand
     * @param cids
     */
    public void updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKey(brand);

        brandMapper.deleteCategoryBrand(brand.getId());
        for(Long cid :cids){
            brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }

    /**
     * 根据id删除品牌数据
     * @param id
     */
    public void deleteBrand(Long id) {
        brandMapper.deleteCategoryBrand(id);
        brandMapper.deleteByPrimaryKey(id);

    }

    /**
     * 根据传过来的id查询品牌集合
     * @param cid
     * @return
     */
    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> list=brandMapper.queryBrandByCid(cid);
        if (CollectionUtils.isEmpty(list)){
            throw new WlkgException(ExceptionEnums.BRAND_IS_NOT);
        }
        return list;
    }

    /**
     * 根据id查询品牌对象
     * @param brandId
     * @return
     */
    public Brand selectBrandById(Long brandId) {
      return   brandMapper.selectByPrimaryKey(brandId);
    }


}


package com.wlkg.mapper;

import com.wlkg.pojo.Brand;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand (category_id,brand_id) values(#{cid},#{id})")
    int insertCategoryBrand(@Param("cid")Long cid, @Param("id") Long id);

    @Delete("delete from tb_category_brand WHERE brand_id = #{id} ")
    void deleteCategoryBrand( @Param("id") Long id);

    @Select("select tb_brand.* from tb_brand join tb_category_brand on tb_brand.id=tb_category_brand.brand_id where tb_category_brand.category_id=#{cid}")
    List<Brand> queryBrandByCid(@Param("cid")Long cid);
}

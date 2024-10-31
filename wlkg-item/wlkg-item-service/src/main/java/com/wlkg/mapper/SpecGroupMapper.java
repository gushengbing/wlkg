package com.wlkg.mapper;

import com.wlkg.pojo.SpecGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SpecGroupMapper extends Mapper<SpecGroup> {
    @Delete("delete from tb_spec_param where group_id = #{id}")
    void deleteSpecGroup(@Param("id") Long id);
}

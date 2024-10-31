package com.wlkg.mapper;

import com.wlkg.pojo.Stock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StockMapper extends Mapper<Stock>, InsertListMapper<Stock>,DeleteByIdListMapper<Stock,Long> {
  /*  @Delete({
            "<script>",
                "delete",
                "from tb_stock",
                "where sku_id in",
                 "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
                     "#{id}",
                 "</foreach>",
            "</script>"
    })
   // void deleteByIdList(@Param("ids")List<Long> ids);*/
}

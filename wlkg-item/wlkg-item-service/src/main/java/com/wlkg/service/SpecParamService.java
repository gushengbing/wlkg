/*
package com.wlkg.service;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.mapper.SpecParamMapper;
import com.wlkg.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecParamService {
    @Autowired
    private SpecParamMapper specParamMapper;

    */
/**
     * 查询参数规格
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     * @return
     *//*

    public List<SpecParam> querySpecParam(Long gid, Long cid, Boolean searching, Boolean generic) {

        SpecParam specParam=new SpecParam();
        if (gid!=null){
            specParam.setGroupId(gid);
        }
        if (cid!=null){
            specParam.setCid(cid);
        }
        if (searching!=null){
            specParam.setSearching(searching);
        }
        if (generic!=null){
            specParam.setGeneric(generic);
        }

        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            throw new WlkgException(ExceptionEnums.SPECPARAM_IS_NOT_FOUND);
        }
        return list;
    }

    */
/**
     * 添加参数规格
     * @param specParam
     *//*

    public void insertSpecParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    */
/**
     * 修改参数规格
     * @param specParam
     *//*

    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKeySelective(specParam);
    }

    */
/**
     * 删除参数规格
     * @param id
     *//*

    public void deletepecParam(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }
}
*/

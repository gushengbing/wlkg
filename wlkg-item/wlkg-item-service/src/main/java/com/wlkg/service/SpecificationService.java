package com.wlkg.service;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.mapper.SpecGroupMapper;
import com.wlkg.mapper.SpecParamMapper;
import com.wlkg.pojo.SpecGroup;
import com.wlkg.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.connection.ConnectionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(specGroup);
       if (CollectionUtils.isEmpty(list)){
            throw  new WlkgException(ExceptionEnums.SPECGROUP_IS_NOT_FOUND);
        }
        return list;
    }

    public List<SpecGroup> querySpecsByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> groups = this.querySpecGroupByCid(cid);
        // 查询当前分类下的参数
        List<SpecParam> specParams = querySpecParam(null, cid, null, null);
        Map<Long, List<SpecParam>> map = new HashMap<>();

        for (SpecParam param : specParams){
            if(!map.containsKey(param.getGroupId())){
                //这个组id在map中不存在，新增一个list
                map.put(param.getGroupId(), new ArrayList<>());
            }

            map.get(param.getGroupId()).add(param);
        }
        //填充param到group
        for (SpecGroup specGroup: groups){
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return groups;
    }
    /**
     * 添加分组
     * @param specGroup
     */
    public void insertSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    /**
     * 修改分组
     * @param specGroup
     */
    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKeySelective(specGroup);
    }

    /**
     * 删除分组
     * @param id
     */
    public void deleteSpecGroup(Long id) {
        specGroupMapper.deleteSpecGroup(id);
        specGroupMapper.deleteByPrimaryKey(id);
    }


    /**
     * 查询参数规格
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     * @return
     */
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

    /**
     * 添加参数规格
     * @param specParam
     */
    public void insertSpecParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    /**
     * 修改参数规格
     * @param specParam
     */
    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKeySelective(specParam);
    }

    /**
     * 删除参数规格
     * @param id
     */
    public void deletepecParam(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }
}

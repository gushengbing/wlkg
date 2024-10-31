package com.wlkg.service;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.mapper.CategoryMapper;
import com.wlkg.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据id查询分类集合
     * @param pid
     * @return
     */
    public List<Category> queryCategoryByPid(Long pid) {
        Category c = new Category();
        c.setParentId(pid);
        List<Category> list= categoryMapper.select(c);
        if(CollectionUtils.isEmpty(list)){
            throw new WlkgException(ExceptionEnums.CATEGORY_IS_EMPTY);
        }
        return list;
    }

    /**
     * 添加分类对象
     * @param category
     * @return
     */
    public int addCategory(Category category) {
        if (category!=null&&category.getParentId()>0){
            Category category2 = categoryMapper.selectByPrimaryKey(category.getParentId());
            if (category2.getIsParent()==false){
                category2.setIsParent(true);
                int i = categoryMapper.updateByPrimaryKey(category2);
                if (i>0){
                    return  categoryMapper.insert(category);
                }
            }else {
                return categoryMapper.insert(category);
            }
        }
        throw new WlkgException(ExceptionEnums.CATEGORY_IS_EMPTY);
    }

    /**
     * 根据id删除分类对象
     * @param id
     */
    public void deleteCategory(Long id) {
        /*Category category1 = categoryMapper.selectByPrimaryKey(id);
        Category category2 = categoryMapper.selectByPrimaryKey(category1.getParentId());
        category2.setIsParent(false);
        categoryMapper.updateByPrimaryKeySelective(category2);*/

        categoryMapper.deleteByPrimaryKey(id);
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId", id);
            List<Category> list = categoryMapper.selectByExample(example);
            System.out.println("删除集合对象:" + list);
            for (Category category : list) {
                if (!category.getIsParent()) {
                    Category cte=new Category();
                    cte.setParentId(category.getParentId());
                    List<Category> select = categoryMapper.select(cte);
                    for (Category c:select){
                        categoryMapper.delete(c);
                    }

                } else {
                    categoryMapper.delete(category);
                    deleteCategory(category.getId());
                }
            }


    }

    /**
     * 根据对象来修改分类对象
     * @param category
     * @return
     */
    public int updateCategory(Category category) {
        if (category!=null&&category.getId()>0){
            Category category1 = categoryMapper.selectByPrimaryKey(category.getId());
            if (category1!=null){
                category1.setName(category.getName());
                return categoryMapper.updateByPrimaryKeySelective(category1);
            }
        }/*else if (category.getId()==0&&category.getParentId()!=0&&category.getSort()!=0){
            Category category1=new Category();
            category1.setName(category.getName());
            category1.setParentId(category.getParentId());
            category1.setSort(category.getSort());
            return  categoryMapper.updateByPrimaryKey(category1);

        }*/
        throw new WlkgException(ExceptionEnums.CATEGORY_IS_EMPTY);
    }

    /**
     * 根据id查询分类集合
     * @param bid
     * @return
     */
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }

    /**
     * 根据集合id查询分类对象名称
     * @param ids
     * @return
     */
    public List<String> queryNameByIds(List<Long> ids) {//map((s)->s.getName)
        return categoryMapper.selectByIdList(ids).stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
     * 根据分类id集合查询每个分类对象
     * @param ids
     * @return
     */
    public List<Category> queryByIds(List<Long> ids) {
        List<Category> list = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)){
            throw  new WlkgException(ExceptionEnums.CATEGORY_IS_EMPTY);
        }
        return list;
    }
}

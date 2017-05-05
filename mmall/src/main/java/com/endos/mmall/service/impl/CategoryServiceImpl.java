package com.endos.mmall.service.impl;

import com.endos.mmall.common.Const;
import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.dao.CategoryMapper;
import com.endos.mmall.entity.Category;
import com.endos.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Endos on 2017/05/04.
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 增加分类
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServiceResponse addCategory(String categoryName, Integer parentId) {
        if (StringUtils.isBlank(categoryName) || parentId == null){
            return ServiceResponse.createByErrorMessage(Const.Message.EMPTY_PARAM);
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);// 默认这个分类是可用的

        int resultCount = categoryMapper.insert(category);
        if (resultCount > 0) {
            return ServiceResponse.createBySuccessMessage(Const.Message.ADD_CATEGORY_SUCCESS);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.ADD_CATEGORY_FAIL);
    }

    /**
     * 修改分类名称
     * @param categoryName
     * @param categoryId
     * @return
     */
    @Override
    public ServiceResponse updateCategoryName(String categoryName, Integer categoryId) {
        if (StringUtils.isBlank(categoryName) || categoryId == null) {
            return ServiceResponse.createByErrorMessage(Const.Message.EMPTY_PARAM);
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int resultCount = categoryMapper.updateByPrimaryKey(category);
        if (resultCount > 0){
            return ServiceResponse.createBySuccessMessage(Const.Message.UPDATE_CATEGORY_SUCCESS);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.UPDATE_CATEGORY_FAIL);
    }

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     * @param categoryId
     * @return
     */
    @Override
    public ServiceResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        return null;
    }
}

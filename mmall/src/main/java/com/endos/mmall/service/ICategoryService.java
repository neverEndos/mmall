package com.endos.mmall.service;

import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.entity.Category;

import java.util.List;

/**
 * Created by Endos on 2017/05/04.
 */
public interface ICategoryService {

    /**
     * 增加分类
     * @param categoryName
     * @param parentId
     * @return
     */
    ServiceResponse addCategory(String categoryName, Integer parentId);

    /**
     * 修改分类名称
     * @param categoryName
     * @param categoryId
     * @return
     */
    ServiceResponse updateCategoryName(String categoryName, Integer categoryId);

    /**
     * 查询子节点的category信息,并且不递归,保持平级
     * @param categoryId
     * @return
     */
    ServiceResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
}

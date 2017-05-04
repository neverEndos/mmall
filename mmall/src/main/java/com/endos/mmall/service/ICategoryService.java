package com.endos.mmall.service;

import com.endos.mmall.common.ServiceResponse;

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
     * @param parentId
     * @return
     */
    ServiceResponse updateCategoryName(String categoryName, Integer parentId);
}

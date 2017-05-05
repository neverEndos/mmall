package com.endos.mmall.controller.backend;

import com.endos.mmall.common.Const;
import com.endos.mmall.common.ResponseCode;
import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.entity.User;
import com.endos.mmall.service.ICategoryService;
import com.endos.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Endos on 2017/05/04.
 */
@Controller
@RequestMapping("/manager/category")
public class CategoryManageController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add_category.do")
    @ResponseBody
    public ServiceResponse addCategory(String categoryName, @RequestParam(value = "categoryId", defaultValue = "0") Integer parentId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), Const.Message.NEED_LOGIN);
        }
        // 检验是否为管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            // 是管理员，执行添加分类逻辑
            return categoryService.addCategory(categoryName, parentId);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.NOT_ADMIN);
    }

    @PostMapping("/set_category_name.do")
    @ResponseBody
    public ServiceResponse setCategoryName(String categoryName, Integer categoryId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), Const.Message.NEED_LOGIN);
        }
        // 检验是否为管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.updateCategoryName(categoryName, categoryId);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.NOT_ADMIN);
    }

    @PostMapping("get_category.do")
    @ResponseBody
    public ServiceResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), Const.Message.NEED_LOGIN);
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //查询子节点的category信息,并且不递归,保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.NOT_ADMIN);
    }
}

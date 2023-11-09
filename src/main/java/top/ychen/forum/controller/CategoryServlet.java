package top.ychen.forum.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.ychen.forum.service.CategoryService;
import top.ychen.forum.domain.entity.Category;
import top.ychen.forum.service.impl.CategoryServiceImpl;

import java.util.List;

/**
 * @author ychen
 * @date 2023/10/24
 * @description CategoryServlet
 **/
@WebServlet(name = "categoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends BaseServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 返回全部分类
     */
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<Category> list = categoryService.list();
        System.out.println(list.toString());
        request.setAttribute("categoryList", list);
    }
}

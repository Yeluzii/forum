package top.ychen.forum.service.impl;

import top.ychen.forum.domain.entity.Category;
import top.ychen.forum.dao.CategoryDao;
import top.ychen.forum.service.CategoryService;

import java.util.List;

/**
 * @author mqxu
 * @date 2023/10/24
 * @description CategoryServiceImpl
 **/
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();

    @Override
    public List<Category> list() {
        return categoryDao.list();
    }
}

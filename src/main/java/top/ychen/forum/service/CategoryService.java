package top.ychen.forum.service;

import top.ychen.forum.domain.entity.Category;

import java.util.List;

/**
 * @author mqxu
 * @date 2023/10/24
 * @description CategoryService
 **/
public interface CategoryService {
    /**
     * 全部分类
     *
     * @return {@link List<Category>}
     */
    List<Category> list();
}

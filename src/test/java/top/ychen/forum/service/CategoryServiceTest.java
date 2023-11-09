package top.ychen.forum.service;

import org.junit.jupiter.api.Test;
import top.ychen.forum.domain.entity.Category;
import top.ychen.forum.service.impl.CategoryServiceImpl;

import java.util.List;

class CategoryServiceTest {

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Test
    void list() {
        List<Category> list = categoryService.list();
        list.forEach(System.out::println);
    }
}
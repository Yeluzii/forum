package top.ychen.forum.dao;

import top.ychen.forum.domain.entity.Category;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.ychen.forum.util.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mqxu
 * @date 2023/10/24
 * @description CategoryDao
 **/
public class CategoryDao {
    private final QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

    //开启驼峰映射
    private final BeanProcessor beanProcessor = new GenerousBeanProcessor();

    private final RowProcessor processor = new BasicRowProcessor(beanProcessor);


    /**
     * 根 据id 找分类
     *
     * @param id 分类id
     * @return 分类
     */
    public Category findById(int id) {
        String sql = "SELECT * FROM category WHERE id=? ";
        Category category = null;
        try {
            category = queryRunner.query(sql, new BeanHandler<>(Category.class, processor), id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return category;
    }


    /**
     * 返回分类列表
     *
     * @return 分类列表
     */
    public List<Category> list() {
        String sql = "SELECT * FROM category ORDER BY weight DESC ";
        List<Category> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(Category.class, processor));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
}

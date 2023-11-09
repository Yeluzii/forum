package top.ychen.forum.dao;

import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.util.DataSourceUtil;

/**
 * @author ychen
 * @date 2023/10/24
 * @description UserDao
 **/
public class UserDao {

    private final QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    private final BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private final RowProcessor processor = new BasicRowProcessor(beanProcessor);


    public int save(User user) throws Exception {
        String sql = "INSERT INTO user (phone,username,pwd,gender,img,role,create_time) VALUES (?,?,?,?,?,?,?) ";
        Object[] params = {user.getPhone(), user.getUsername(), user.getPwd(), user.getGender(), user.getImg(), user.getRole(), user.getCreateTime()};
        int i;
        try {
            i = queryRunner.update(sql, params);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Exception();
        }
        return i;
    }


    public User findByPhoneAndPwd(String phone, String md5pwd) {
        String sql = "SELECT * FROM user WHERE phone=? AND pwd=? ";
        User user = null;
        try {
            user = queryRunner.query(sql, new BeanHandler<>(User.class, processor), phone, md5pwd);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return user;
    }
}

package top.ychen.forum.service;


import top.ychen.forum.domain.entity.User;

/**
 * @author moqi
 */
public interface UserService {
    int register(User user);

    User login(String phone, String pwd);
}

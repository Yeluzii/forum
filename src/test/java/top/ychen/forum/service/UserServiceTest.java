package top.ychen.forum.service;

import org.junit.jupiter.api.Test;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserService userService = new UserServiceImpl();

    @Test
    void register() {
        User user = User.builder().phone("13811112222").username("张三").pwd("123456").gender(0).build();
        int count = userService.register(user);
        System.out.println(count);
    }

    @Test
    void login() {
        User user = userService.login("13951901111", "123456");
        System.out.println(user);
    }
}
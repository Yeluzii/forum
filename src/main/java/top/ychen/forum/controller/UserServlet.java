package top.ychen.forum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.service.UserService;
import top.ychen.forum.service.impl.UserServiceImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ychen
 * @date 2023/10/24
 * @description UserServlet
 **/

@WebServlet(name = "userServlet", urlPatterns = {"/user"})
public class UserServlet extends BaseServlet {
    private final UserService userService = new UserServiceImpl();


    /**
     * <a href="http://localhost:8080/user?method=register">...</a>
     * 注册
     *
     * @param req  请求
     * @param resp 响应
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        Map<String, String[]> map = req.getParameterMap();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
        int i = userService.register(user);
        if (i > 0) {
            //注册成功，跳转到登录界面
            req.getRequestDispatcher("/user/login.jsp").forward(req, resp);
        } else {
            //注册失败，跳转到注册页面
            req.getRequestDispatcher("/user/register.jsp").forward(req, resp);
        }
    }

    /**
     * <a href="http://localhost:8080/user?method=login">...</a>
     * 登录
     *
     * @param req  请求
     * @param resp 响应
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String phone = req.getParameter("phone");
        String pwd = req.getParameter("pwd");
        User user = userService.login(phone, pwd);
        if (user != null) {
            req.getSession().setAttribute("loginUser", user);
            //跳转页面
            resp.sendRedirect("/topic?method=list&c_id=1");
        } else {
            req.setAttribute("msg", "用户名或者密码不正确");
            req.getRequestDispatcher("/user/login.jsp").forward(req, resp);
        }
    }


    /**
     * <a href="http://localhost:8080/user?method=logout">...</a>
     * 退出登录
     *
     * @param req  请求
     * @param resp 响应
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("/topic?method=list&c_id=1");
    }

}
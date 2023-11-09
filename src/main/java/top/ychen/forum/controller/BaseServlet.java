package top.ychen.forum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author moqi
 * 封装通用 Servlet
 */
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {


    /**
     * 子类的Servlet被访问，会调用service方法，子类没有重写，那么就会调用父类service方法
     *
     * @param req  请求对象
     * @param resp 响应对象
     * @throws ServletException 异常
     * @throws IOException      异常
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //获取请求方法
        String method = req.getParameter("method");
        if (method != null) {
            try {
                //获得当前被访问对象的字节码对象，和字节码对象里面指定的方法
                Method targetMethod = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
                //执行对应的方法
                targetMethod.invoke(this, req, resp);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

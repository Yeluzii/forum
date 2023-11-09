package top.ychen.forum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.ychen.forum.domain.dto.PageDto;
import top.ychen.forum.domain.entity.Reply;
import top.ychen.forum.domain.entity.Topic;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.service.CategoryService;
import top.ychen.forum.service.TopicService;
import top.ychen.forum.service.impl.CategoryServiceImpl;
import top.ychen.forum.service.impl.TopicServiceImpl;

import java.io.IOException;

/**
 * @author ychen
 * @date 2023/10/24
 * @description TopicServlet
 **/
@WebServlet(name = "topicServlet", urlPatterns = {"/topic"})
public class TopicServlet extends BaseServlet {
    private final TopicService topicService = new TopicServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 默认分页大小
     */
    private static final int PAGE_SIZE = 5;

    /**
     * <a href="http://localhost:8080/topic?method=list&c_id=2&page=2">...</a>
     * topic分页接口
     *
     * @param req  request
     * @param resp response
     */
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cId = Integer.parseInt(req.getParameter("c_id"));
        //默认第一页
        int page = 1;
        String currentPage = req.getParameter("page");
        if (currentPage != null && !currentPage.isEmpty()) {
            page = Integer.parseInt(currentPage);
        }
        PageDto<Topic> pageDTO = topicService.listTopicPageByCid(cId, page, PAGE_SIZE);
        System.out.println(pageDTO.toString());
        req.getSession().setAttribute("categoryList", categoryService.list());
        req.setAttribute("topicPage", pageDTO);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    /**
     * <a href="http://localhost:8080/topic?method=addTopic">...</a>
     * 发布主题
     *
     * @param req  request
     * @param resp response
     */
    public void addTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            req.setAttribute("msg", "请登录");
            resp.sendRedirect("/user/login.jsp");
            return;
            //页面跳转 TODO
        }
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        int cId = Integer.parseInt(req.getParameter("c_id"));
        topicService.addTopic(loginUser, title, content, cId);
        //发布主题成功
        resp.sendRedirect("/topic?method=list&c_id=" + cId);
    }

    /**
     * <a href="http://localhost:8080/topic?method=replyByTopicId&topic_id=9">...</a>
     * 盖楼回复
     *
     * @param req  request
     * @param resp response
     */
    public void replyByTopicId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            req.setAttribute("msg", "请登录");
            resp.sendRedirect("/user/login.jsp");
            return;
        }
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        String content = req.getParameter("content");
        topicService.replyByTopicId(loginUser, topicId, content);
        resp.sendRedirect("/topic?method=findDetailById&topic_id=" + topicId + "&page=1");
    }

    /**
     * <a href="http://localhost:8080/topic?method=findDetailById&topic_id=1&page=1">...</a>
     * 查看主题的全部回复
     *
     * @param req  request
     * @param resp response
     */
    public void findDetailById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取 topic_id
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        //默认第一页
        int page = 1;
        String currentPage = req.getParameter("page");
        if (currentPage != null && !currentPage.isEmpty()) {
            page = Integer.parseInt(currentPage);
        }
        //处理浏览量，如果同个session内只算一次
        String sessionReadKey = "is_read_" + topicId;
        Boolean isRead = (Boolean) req.getSession().getAttribute(sessionReadKey);
        if (isRead == null) {
            req.getSession().setAttribute(sessionReadKey, true);
            //新增一个pv
            topicService.addOnePV(topicId);
        }
        Topic topic = topicService.findById(topicId);
        PageDto<Reply> pageDTO = topicService.findReplyPageByTopicId(topicId, page, PAGE_SIZE);
        System.out.println(pageDTO.toString());
        req.setAttribute("topic", topic);
        req.setAttribute("replyPage", pageDTO);
        req.getRequestDispatcher("/topic_detail.jsp").forward(req, resp);
    }


}
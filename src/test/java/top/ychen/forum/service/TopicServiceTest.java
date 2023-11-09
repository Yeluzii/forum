package top.ychen.forum.service;

import org.junit.jupiter.api.Test;
import top.ychen.forum.domain.dto.PageDto;
import top.ychen.forum.domain.entity.Reply;
import top.ychen.forum.domain.entity.Topic;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.service.impl.TopicServiceImpl;
import top.ychen.forum.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class TopicServiceTest {
    private final TopicService topicService = new TopicServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Test
    void listTopicPageByCid() {
        PageDto<Topic> topicPage = topicService.listTopicPageByCid(1, 1, 2);
        System.out.println(topicPage);
    }

    @Test
    void findById() {
        Topic topic = topicService.findById(20);
        System.out.println(topic);
    }

    @Test
    void addTopic() {
        User loginUser = userService.login("13951901111", "123456");
        topicService.addTopic(loginUser, "测试标题", "测试标题", 1);
    }

    @Test
    void findReplyPageByTopicId() {
        PageDto<Reply> replyPage = topicService.findReplyPageByTopicId(22, 1, 2);
        System.out.println(replyPage);
    }

    @Test
    void replyByTopicId() {
        User loginUser = userService.login("13951901111", "123456");
        int count = topicService.replyByTopicId(loginUser, 22, "测试回复");
        System.out.println(count);
    }

    @Test
    void addOnePV() {
        topicService.addOnePV(22);
    }

}
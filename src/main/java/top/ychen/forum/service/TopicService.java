package top.ychen.forum.service;

import top.ychen.forum.domain.dto.PageDto;
import top.ychen.forum.domain.entity.Reply;
import top.ychen.forum.domain.entity.Topic;
import top.ychen.forum.domain.entity.User;

/**
 * @author ychen
 * @date 2023/10/24
 * @description TopicService
 **/
public interface TopicService {
    int addTopic(User loginUser, String title, String content, int cId);
    PageDto<Reply> findReplyPageByTopicId(int topicId, int page, int pageSize);

    int replyByTopicId(User loginUser, int topicId, String content);
    PageDto<Topic> listTopicPageByCid(int cId, int page, int pageSize);

    Topic findById(int topicId);
    void addOnePV(int topicId);

}
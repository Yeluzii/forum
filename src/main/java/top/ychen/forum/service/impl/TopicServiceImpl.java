package top.ychen.forum.service.impl;

import top.ychen.forum.dao.CategoryDao;
import top.ychen.forum.dao.ReplyDao;
import top.ychen.forum.domain.dto.PageDto;
import top.ychen.forum.dao.TopicDao;
import top.ychen.forum.domain.entity.Category;
import top.ychen.forum.domain.entity.Reply;
import top.ychen.forum.domain.entity.Topic;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.service.TopicService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ychen
 * @date 2023/10/24
 * @description TopicServiceImpl
 **/
public class TopicServiceImpl implements TopicService {
    private final TopicDao topicDao = new TopicDao();
    private final CategoryDao categoryDao = new CategoryDao();

    /**
     * 分页查询
     *
     * @param cId      分类id
     * @param page     页码
     * @param pageSize 每页显示的记录数
     * @return PageDto<Topic>
     */
    @Override
    public PageDto<Topic> listTopicPageByCid(int cId, int page, int pageSize) {
        //查询总记录数
        int totalRecordNum = topicDao.countTotalTopicByCid(cId);
        int from = (page - 1) * pageSize;
        //分页查询
        List<Topic> topicList = topicDao.findListByCid(cId, from, pageSize);
        PageDto<Topic> pageDTO = new PageDto<>(page, pageSize, totalRecordNum);
        pageDTO.setList(topicList);
        return pageDTO;
    }

    /**
     * 根据id查询
     *
     * @param topicId 主题id
     * @return Topic
     */
    @Override
    public Topic findById(int topicId) {
        return topicDao.findById(topicId);
    }

    @Override
    public int addTopic(User loginUser, String title, String content, int cId) {
        Category category = categoryDao.findById(cId);
        if (category == null) {
            return 0;
        }
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCreateTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        topic.setPv(1);
        topic.setDelete(0);
        topic.setUserId(loginUser.getId());
        topic.setUsername(loginUser.getUsername());
        topic.setUserImg(loginUser.getImg());
        topic.setCId(cId);
        topic.setHot(0);
        int rows = 0;
        try {
            rows = topicDao.save(topic);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rows;
    }

    private final ReplyDao replyDao = new ReplyDao();

    /**
     * 分页查询回复
     *
     * @param topicId  主题id
     * @param page     页码
     * @param pageSize 每页显示的记录数
     * @return PageDto<Reply>
     */
    @Override
    public PageDto<Reply> findReplyPageByTopicId(int topicId, int page, int pageSize) {
        //查询总的回复
        int totalRecordNum = replyDao.countTotalReplyByCid(topicId);
        int from = (page - 1) * pageSize;
        //分页查询
        List<Reply> replyList = replyDao.findListByTopicId(topicId, from, pageSize);
        PageDto<Reply> pageDTO = new PageDto<>(page, pageSize, totalRecordNum);
        pageDTO.setList(replyList);
        return pageDTO;
    }

    /**
     * 根据主题id查询最新的回复
     *
     * @param loginUser 登录用户
     * @param topicId   主题id
     * @param content   回复内容
     * @return int
     */
    @Override
    public int replyByTopicId(User loginUser, int topicId, String content) {
        int floor = topicDao.findLatestFloorByTopicId(topicId);
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setCreateTime(LocalDateTime.now());
        reply.setUpdateTime(LocalDateTime.now());
        reply.setFloor(floor + 1);
        reply.setTopicId(topicId);
        reply.setUserId(loginUser.getId());
        reply.setUsername(loginUser.getUsername());
        reply.setUserImg(loginUser.getImg());
        reply.setDelete(0);
        return replyDao.save(reply);
    }

    /**
     * 根据主题id查询最新的回复
     *
     * @param topicId 主题id
     */
    @Override
    public void addOnePV(int topicId) {
        Topic topic = topicDao.findById(topicId);
        int newPV = topic.getPv() + 1;
        topicDao.updatePV(topicId, newPV, topic.getPv());
    }

}
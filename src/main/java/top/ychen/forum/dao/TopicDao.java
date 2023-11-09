package top.ychen.forum.dao;

import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.ychen.forum.domain.entity.Topic;
import top.ychen.forum.util.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author ychen
 * @date 2023/10/24
 * @description TopicDao
 **/
public class TopicDao {
    private final QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    private final BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private final RowProcessor processor = new BasicRowProcessor(beanProcessor);


    /**
     * 根据分类id查询主题总数
     *
     * @param cId 分类id
     * @return 总行数
     */
    public int countTotalTopicByCid(int cId) {
        String sql = "SELECT count(*) FROM topic WHERE c_id=? AND `delete`=0 ";
        Long count = null;
        try {
            count = (Long) queryRunner.query(sql, new ScalarHandler<>(), cId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        assert count != null;
        return count.intValue();
    }


    /**
     * 根据分类id分页查询
     *
     * @param cId      分类id
     * @param from     起始行
     * @param pageSize 每页大小
     * @return 话题列表
     */
    public List<Topic> findListByCid(int cId, int from, int pageSize) {
        String sql = "SELECT * FROM topic WHERE c_id=? AND `delete`=0 ORDER BY update_time DESC LIMIT ?,? ";
        List<Topic> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(Topic.class, processor), cId, from, pageSize);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return list;
    }


    /**
     * 根据话题id查询话题
     *
     * @param topicId 话题 id
     * @return 话题
     */
    public Topic findById(int topicId) {
        String sql = "select * from topic where id = ?";
        Topic topic = null;
        try {
            topic = queryRunner.query(sql, new BeanHandler<>(Topic.class, processor), topicId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return topic;
    }

    /**
     * 保存话题
     *
     * @param topic 话题
     * @return 影响行数
     * @throws Exception 异常
     */
    public int save(Topic topic) throws Exception {
        String sql = "INSERT INTO topic(c_id,title,content,pv,user_id,username,user_img,create_time,update_time,hot,`delete`) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
        Object[] params = {
                topic.getCId(),
                topic.getTitle(),
                topic.getContent(),
                topic.getPv(),
                topic.getUserId(),
                topic.getUsername(),
                topic.getUserImg(),
                topic.getCreateTime(),
                topic.getUpdateTime(),
                topic.getHot(),
                topic.getDelete()
        };
        int i;
        try {
            i = queryRunner.update(sql, params);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Exception();
        }
        return i;
    }

    /**
     * 查找最新楼层的问题
     *
     * @param topicId 话题id
     * @return 最新楼层
     */
    public int findLatestFloorByTopicId(int topicId) {
        //楼层是0，返回后才加1
        int defaultFloor = 0;
        String sql = "SELECT floor FROM reply WHERE topic_id=? ORDER BY floor DESC LIMIT 1 ";
        Integer floor;
        try {
            floor = queryRunner.query(sql, new ScalarHandler<>(), topicId);
            return Objects.requireNonNullElse(floor, defaultFloor);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    /**
     * 更新浏览量
     *
     * @param topicId 话题id
     * @param newPV   新的浏览量
     * @param oldPV   旧的浏览量
     * @return 影响行数
     */
    public int updatePV(int topicId, int newPV, int oldPV) {
        String sql = "UPDATE topic SET pv=? WHERE pv=? AND id=? ";
        int rows = 0;
        try {
            rows = queryRunner.update(sql, newPV, oldPV, topicId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rows;
    }

}
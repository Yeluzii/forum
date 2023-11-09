package top.ychen.forum.dao;

import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.ychen.forum.domain.entity.Reply;
import top.ychen.forum.util.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ychen
 * @date 2023/10/24
 * @description ReplyDao
 **/
public class ReplyDao {
    private final QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    private final BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private final RowProcessor processor = new BasicRowProcessor(beanProcessor);


    /**
     * 根据主题id 查询回复总记录
     *
     * @param topicId 主题id
     * @return 回复总记录
     */
    public int countTotalReplyByCid(int topicId) {
        String sql = "SELECT COUNT(*) FROM reply WHERE topic_id=? ";
        Long count = null;
        try {
            count = (Long) queryRunner.query(sql, new ScalarHandler<>(), topicId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        assert count != null;
        return count.intValue();
    }


    /**
     * 根据主题id 查询回复列表
     *
     * @param topicId  主题id
     * @param from     起始位置
     * @param pageSize 每页显示条数
     * @return 回复列表
     */
    public List<Reply> findListByTopicId(int topicId, int from, int pageSize) {
        String sql = "SELECT * FROM reply WHERE topic_id=? ORDER BY create_time LIMIT ?,? ";
        List<Reply> replyList = null;
        try {
            replyList = queryRunner.query(sql, new BeanListHandler<>(Reply.class, processor), topicId, from, pageSize);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return replyList;
    }


    /**
     * 保存回复
     *
     * @param reply 回复
     * @return 影响行数
     */
    public int save(Reply reply) {
        String sql = "INSERT INTO reply (topic_id,floor,content,user_id,username,user_img,create_time,update_time,`delete`)" + " VALUES (?,?,?,?,?,?,?,?,?) ";
        Object[] params = {reply.getTopicId(), reply.getFloor(), reply.getContent(), reply.getUserId(), reply.getUsername(), reply.getUserImg(), reply.getCreateTime(), reply.getUpdateTime(), reply.getDelete()};
        int rows = 0;
        try {
            rows = queryRunner.update(sql, params);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rows;
    }
}

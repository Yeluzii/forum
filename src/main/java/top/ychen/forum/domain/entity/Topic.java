package top.ychen.forum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author moqi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {

    private int id;

    private int cId;

    private String title;

    private String content;

    private int pv;

    private int userId;

    private String username;

    private String userImg;

    private int hot;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private int delete;
}

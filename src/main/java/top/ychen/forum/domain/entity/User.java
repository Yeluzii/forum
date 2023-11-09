package top.ychen.forum.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author moqi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id;

    private String phone;

    private String username;

    private String pwd;

    private int gender;

    private String img;

    private int role;

    private LocalDateTime createTime;

}

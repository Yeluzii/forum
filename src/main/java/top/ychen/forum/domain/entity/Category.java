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
public class Category {

    private int id;

    private String name;

    private int weight;

    private LocalDateTime createTime;
}

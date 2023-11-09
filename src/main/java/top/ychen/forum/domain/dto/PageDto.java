package top.ychen.forum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 *
 * @author moqi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto<T> {

    /**
     * 当前页码
     */
    private int pageNumber;


    /**
     * 每页显示的记录数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private int totalRecord;


    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 数据集合
     */
    private List<T> list;


    public PageDto(int pageNumber, int pageSize, int totalRecord) {

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

        this.totalRecord = totalRecord;

        //计算总页数
        if (totalRecord % pageSize == 0) {

            totalPage = totalRecord / pageSize;

        } else {
            totalPage = totalRecord / pageSize + 1;
        }

    }
}

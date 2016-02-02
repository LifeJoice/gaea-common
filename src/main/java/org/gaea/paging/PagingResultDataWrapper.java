package org.gaea.paging;

import java.util.List;

/**
 *
 * @author Iverson 2014年8月22日 星期五
 */
public class PagingResultDataWrapper<T> {

    public PagingResultDataWrapper() {
    }

    public PagingResultDataWrapper(List<T> data) {
        this.data = data;
    }

    private List<T> data;
    private Integer start;
    private Integer pageSize;
    private Integer total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}

package org.gaea.paging;

/**
 * 储存分页信息的bean。
 *
 * @author Iverson 2014年9月3日 星期三
 */
public class PagingBean {
    
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    private Integer start;
    private Integer pageSize;
    private Integer total;

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

    /**
     * start和pageSize任一为空，结果都为TRUE。
     * @return 
     */
    public Boolean isNull() {
        if (this.start == null || this.start < 0) {
            return true;
        }
        if (this.pageSize == null || this.pageSize < 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 初始化分页对象。主要是给start和pageSize赋值。
     */
    public void init(){
        if (this.start == null) {
            setStart(0);
        }
        if(this.pageSize==null){
            setPageSize(PagingBean.DEFAULT_PAGE_SIZE);
        }
    }
}

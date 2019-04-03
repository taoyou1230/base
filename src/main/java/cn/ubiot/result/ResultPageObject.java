package cn.ubiot.result;

public class ResultPageObject {
    /**返回数据*/
    private Object rows;
    /**当前页*/
    private int currentPage;
    /**总条数*/
    private long count;

    public ResultPageObject(Object rows,int currentPage,long count){
        this.rows = rows;
        this.currentPage = currentPage;
        this.count = count;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}

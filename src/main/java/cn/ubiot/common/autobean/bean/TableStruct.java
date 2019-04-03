package cn.ubiot.common.autobean.bean;

import java.util.List;

/**
 * 数据表的表结构
 * @author
 *
 */
public class TableStruct {

    private String tableName;//表名
    private List Columns;//所有的列
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public List getColumns() {
        return Columns;
    }
    public void setColumns(List columns) {
        Columns = columns;
    }
    public TableStruct(String tableName, List columns) {
        super();
        this.tableName = tableName;
        Columns = columns;
    }
    public TableStruct() {
        super();
    }
    @Override
    public String toString() {
        return "TableStruct [tableName=" + tableName + ", Columns=" + Columns
                + "]";
    }
}
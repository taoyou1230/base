package cn.ubiot.common.autobean.bean;

/**
 * 数据表的列结构
 * @author
 *
 */
public class ColumnStruct {

    private	String columnName;//字段名称
    private String dataType;//字段类型
    private String columnKey;//字段的键（判断是否为主键）
    public String getColumnName() {
        return columnName;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public ColumnStruct(String columnName, String dataType, String columnKey) {
        super();
        this.columnName = columnName;
        this.dataType = dataType;
        this.columnKey = columnKey;
    }
    public ColumnStruct() {
        super();
    }
    @Override
    public String toString() {
        return "ColumnStruct [columnName=" + columnName + ", dataType="
                + dataType +", columnKey=" + columnKey + "]";
    }
}

package cn.ubiot.common.autobean.dao;

import java.util.List;

/**
 * 获取数据表及其结构的dao层接口
 * @author
 *
 */
public interface GetTablesDao {

    //获得数据库的所有表名
    public List getTablesName();

    //获得数据表中的字段名称、字段类型
    public List getTablesStruct();
}

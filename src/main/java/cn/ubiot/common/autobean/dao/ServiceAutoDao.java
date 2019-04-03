package cn.ubiot.common.autobean.dao;

/**
 * 生成Service接口的dao层接口
 * @author
 *
 */
public interface ServiceAutoDao {

    //通过表名、字段名称、字段类型创建Service接口
    public boolean createService();

    /**
     * 通过实体类创建Service接口
     *
     * @return
     */
    public boolean createServiceByEntity() throws ClassNotFoundException;
}

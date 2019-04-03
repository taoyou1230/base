package cn.ubiot.common.autobean.dao;

/**
 * 生成Dao接口的dao层接口
 * @author
 *
 */
public interface DaoAutoDao {

    //通过表名、字段名称、字段类型创建Dao接口
    public boolean createDao();

    /**
     * 通过实体类创建Dao接口
     * @return
     * @throws ClassNotFoundException
     */
    public boolean createDaoByEntity() throws ClassNotFoundException;
}

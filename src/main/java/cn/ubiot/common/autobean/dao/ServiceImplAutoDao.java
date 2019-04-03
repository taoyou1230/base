package cn.ubiot.common.autobean.dao;

/**
 * 生成ServiceImpl实现类的dao层接口
 * @author
 *
 */
public interface ServiceImplAutoDao {

    //通过表名、字段名称、字段类型创建ServiceImpl实现类
    public boolean createServiceImpl();
    /**
     * 通过实体类创建ServiceImpl实现类
     * @return
     * @throws ClassNotFoundException
     */
    public boolean createServiceImplByEntity() throws ClassNotFoundException;
}
package cn.ubiot.common.autobean.dao;
/**
 * 生成Controller类的dao层接口
 * @author
 *
 */
public interface ControllerAutoDao {

    //通过表名、字段名称、字段类型创建Controller接口
    public boolean createController();

    /**
     * 通过实体类创建Controller接口
     * @return
     * @throws ClassNotFoundException
     */
    public boolean createControllerByEntity() throws ClassNotFoundException;
}

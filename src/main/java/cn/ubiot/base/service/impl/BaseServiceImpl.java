package cn.ubiot.base.service.impl;

import cn.ubiot.base.service.BaseService;
import cn.ubiot.utils.MyMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private MyMapper<T> myMapper;

    /**
     * 根据实体类插入一条数据
     * @param obj 对象实体
     * @return
     */
    public int insert(T obj){
        return myMapper.insert(obj);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param obj
     * @return
     */
    public int insertSelective(T obj){
        return myMapper.insertSelective(obj);
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param list
     * @return
     */
    public int insertList(List<T> list){
        return myMapper.insertList(list);
    }
    /**
     * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
     *
     * @param obj
     * @return
     */
    public int insertUseGeneratedKeys(T obj){
        return myMapper.insertUseGeneratedKeys(obj);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param obj 对象实体
     * @return
     */
    public int updateByPrimaryKey(T obj){
        return myMapper.updateByPrimaryKey(obj);
    }
    /**
     * 根据主键更新属性不为null的值
     *
     * @param obj 对象实体
     * @return
     */
    public int updateByPrimaryKeySelective(T obj){
        return myMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 根据Example条件更新实体`obj`包含的全部属性，null值会被更新
     *
     * @param obj
     * @param example
     * @return
     */
    public int updateByExample(T obj,Object example){
        return myMapper.updateByExample(obj,example);
    }
    /**
     * 根据Example条件更新实体`obj`包含的不是null的属性值
     *
     * @param obj
     * @param example
     * @return
     */
    public int update(T obj,Object example){
        return myMapper.updateByExampleSelective(obj,example);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param obj
     * @return
     */
    public int delete(T obj){
        return myMapper.delete(obj);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    public int deleteByPrimaryKey(Object key){
        return myMapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据Example条件删除数据
     *
     * @param example
     * @return
     */
    public int deleteByExample(Object example){
        return myMapper.deleteByExample(example);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param obj
     * @return
     */
    public List<T> select(T obj){
        return myMapper.select(obj);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    public T selectByPrimaryKey(Object key){
        return myMapper.selectByPrimaryKey(key);
    }
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param obj
     * @return
     */
    public T selectOne(T obj){
        return myMapper.selectOne(obj);
    }
    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    public T selectOneByExample(Object example){
        return myMapper.selectOneByExample(example);
    }
    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    public List<T> selectByExample(Object example){
        return myMapper.selectByExample(example);
    }

    /**
     * 根据example条件和RowBounds进行分页查询
     *
     * @param example
     * @param rowBounds
     * @return
     */
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds){
        return myMapper.selectByExampleAndRowBounds(example,rowBounds);
    }
    /**
     * 查询全部结果
     *
     * @return
     */
    public List<T> selectAll(){
        return myMapper.selectAll();
    }
    /**
     * 根据实体属性和RowBounds进行分页查询
     *
     * @param obj
     * @param rowBounds
     * @return
     */
    public List<T> selectByRowBounds(T obj,RowBounds rowBounds){
        return myMapper.selectByRowBounds(obj,rowBounds);
    }
    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param obj)
     * @return
     */
    public int selectCount(T obj){
        return myMapper.selectCount(obj);
    }
    /**
     * 根据Example条件进行查询总数
     *
     * @param example
     * @return
     */
    public int selectCountByExample(Object example){
        return myMapper.selectCountByExample(example);
    }
    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return false：不存在 true：存在
     */
    public boolean existsWithPrimaryKey(Object key){
        return myMapper.existsWithPrimaryKey(key);
    }
}

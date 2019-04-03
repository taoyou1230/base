package cn.ubiot.base.controller;

import cn.ubiot.common.user.entity.User;
import cn.ubiot.common.user.service.UserService;
import cn.ubiot.result.ResultObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class BaseController {

    private static Logger log = Logger.getLogger(BaseController.class);

    @Resource
    private UserService userService;
    /**
     * 判断并返回添加结果
     * @param count 数据库操作结果
     * @param objName 添加的实体描述
     * @return
     */
    public ResultObject insertResultInfo(int count, String objName, Object obj){
        if(count >0) {
            log.info("添加"+objName+"成功！");
            return new ResultObject(true, "添加成功！", obj);
        }else{
            log.info("添加"+objName+"失败！");
            return new ResultObject(false, "添加失败！", obj);
        }
    }

    /**
     * 判断并返回删除结果
     * @param count 数据库操作结果
     * @param objName 删除的实体描述
     * @return
     */
    public ResultObject deleteResultInfo(int count, String objName){
        if(count >0) {
            log.info("删除"+objName+"成功！");
            return new ResultObject(true, "删除成功！", null);
        }else{
            log.info("删除"+objName+"失败！");
            return new ResultObject(false, "删除失败！", null);
        }
    }

    /**
     * 判断并返回修改结果
     * @param count 数据库操作结果
     * @param objName 修改的实体描述
     * @return
     */
    public ResultObject updateResultInfo(int count, String objName){
        if(count >0) {
            log.info("修改"+objName+"成功！");
            return new ResultObject(true, "修改成功！", null);
        }else{
            log.info("修改"+objName+"失败！");
            return new ResultObject(false, "修改失败！", null);
        }
    }

    /**
     * 判断并返回查询结果
     * @param obj 查询对象的结果
     * @param id 查询对象的id
     * @param objName 查询的实体描述
     * @return
     */
    public ResultObject getObjectResultInfo(Object obj, Integer id,String objName){
        if(obj != null) {
            log.info("查询"+objName+"详情成功！");
            return new ResultObject(true, "查询成功！", obj);
        }else{
            log.info("查询"+objName+"详情失败！id:["+id+"]对应的对象不存在或是sql语句有误！");
            return new ResultObject(false, "查询失败！", null);
        }
    }

    /**
     * 获取查询条件map
     * @param filters 查询条件
     * @param sortInfo 排序条件
     * @return
     */
    public Map<String,Object> getQueryMap(String filters,String sortInfo){
        JSONObject filtersJson = JSON.parseObject(filters);
        JSONObject sortJson = JSON.parseObject(sortInfo);
        return getQueryMap(filtersJson,sortJson);
    }

    /**
     * 获取查询条件map
     * @param filtersJson 查询条件
     * @param sortInfo 排序条件
     * @return
     */
    public Map<String,Object> getQueryMap(JSONObject filtersJson,String sortInfo){
        JSONObject sortJson = JSON.parseObject(sortInfo);
        Map<String,Object> map = new HashMap<String,Object>();
        return getQueryMap(filtersJson,sortJson);
    }

    /**
     * 获取查询条件map
     * @param filters 查询条件
     * @param sortInfo 排序条件
     * @return
     */
    public Map<String,Object> getQueryMap(JSONObject filters,JSONObject sortInfo){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("filters",filters);
        map.put("sortInfo",sortInfo);
        return map;
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public User getUser(){
        Subject currentUser = SecurityUtils.getSubject();
        String principal = (String)currentUser.getPrincipal();
        User user = userService.getUser(principal);
        return user;

    }
}

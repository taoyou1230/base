package cn.ubiot.result;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 返回结果
 */
public class ResultObject {

    /**查询是否成功*/
    private boolean success;
    /**返回信息*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    /**返回数据*/
    private Object data;
    /**错误信息*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JSONObject error;

    public boolean isSuccess() {
        return success;
    }

    public ResultObject(boolean success, String msg, Object data){
        this.success = success;
        this.data = data;
        if(success == false){
            this.error = new JSONObject();
            this.error.put("message",msg);
        }else{
            this.msg = msg;
        }
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSONObject getError() {
        return error;
    }

    public void setError(JSONObject error) {
        this.error = error;
    }
}

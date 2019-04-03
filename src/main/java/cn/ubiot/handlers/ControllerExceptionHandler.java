package cn.ubiot.handlers;

import cn.ubiot.exceptions.ControllerException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


/**
 * Author: Damon.CF
 * Company: UBIOT.CN
 * Date: 2018/12/28$
 * Description:
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 自定义ControllerException
     * @param e
     * @return
     */
    @ExceptionHandler(ControllerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handlerControllerException(ControllerException e){
        e.printStackTrace();
        Map<String,Object> map=new HashMap<>();
        map.put("code", e.getCode());
        map.put("success", false);
        map.put("msg",e.getMessage());
        map.put("handler", "handlerControllerException");
        return map;
    }

    /**
     * 所有异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerException(Exception e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg", e.getMessage());
        map.put("handler", "handlerException");
        return map;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Map<String, Object> handlerAuthenticationException(AuthenticationException e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg", e.getMessage());
        map.put("handler", "handlerAuthenticationException");
        return map;
    }
}

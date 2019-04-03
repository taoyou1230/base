package cn.ubiot.exceptions;

/**
 * Author: Damon.CF
 * Company: UBIOT.CN
 * Date: $date$
 * Description: $desc$
 */
public class ParamException extends RuntimeException {
    private int code;
    private String msg;

    public ParamException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

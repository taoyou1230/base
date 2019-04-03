package cn.ubiot.exceptions;

/**
 * Author: Damon.CF
 * Company: UBIOT.CN
 * Date: 2018/12/28$
 * Description:
 */
public class ControllerException extends RuntimeException {

    private int code;

    public ControllerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

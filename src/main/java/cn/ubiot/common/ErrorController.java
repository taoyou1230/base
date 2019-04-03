package cn.ubiot.common;

import cn.ubiot.result.ResultObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error")
public class ErrorController {

    private static Logger log = Logger.getLogger(ErrorController.class);

    @GetMapping("/500")
    @ResponseBody
    public ResultObject error500() {
        return new ResultObject(false,"500错误！",500);
    }

    @GetMapping("/404")
    @ResponseBody
    public ResultObject error404() {
        return new ResultObject(false,"请求路径有误或是页面不存在！",404);
    }

    @GetMapping("/401")
    @ResponseBody
    public ResultObject error401() {
        return new ResultObject(false,"用户未登陆或是登陆已失效！",401);
    }
}

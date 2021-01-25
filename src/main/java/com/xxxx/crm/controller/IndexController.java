package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author arthur
 * @date 2021/1/22 20:35
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }


    /**
     * 系统界面欢迎页
     * @return
     */
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主页面
     *      根据cookie中的userId得到用户名称
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        // 从cookie中获取解密后的userID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 根据userId查询用户信息，将用户名称设置到请求域中，然后根据el表达式设置到界面
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";
    }

    /**
     * 密码修改页面
     * @return
     */
    @RequestMapping("user/toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }
}

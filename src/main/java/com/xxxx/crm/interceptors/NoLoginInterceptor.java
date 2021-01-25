package com.xxxx.crm.interceptors;

import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未登录拦截
 * @author arthur
 * @date 2021/1/25 14:09
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    /**
     * 获取cookie 解析用户id
     *    如果用户id 存在 并且数据库中存在对应记录 请求合法   反之 用户未登录 请求非法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if (userId == 0 || userService.selectByPrimaryKey(userId) == null){
            throw new NoLoginException();
        }
        return super.preHandle(request, response, handler);
    }
}

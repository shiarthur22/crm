package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.User;

import java.util.List;
import java.util.Map;

/**
 * @author NeXT
 */
public interface UserMapper extends BaseMapper<User,Integer> {
    /**
     * 根据用户名称查询用户信息
     * @param userName
     * @return
     */
    User queryUserByUserName(String userName);

    /**
     * 查询分配人
     * @return
     */
    List<Map<String,Object>> queryAllSales();
}
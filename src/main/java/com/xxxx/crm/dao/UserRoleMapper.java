package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.UserRole;

/**
 * @author NeXT
 */
public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {
    /**
     * 查询用户角色总数量
     * @param userId
     * @return
     */
    int countUserRoleByUserId(Integer userId);

    /**
     * 删除用户角色
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(Integer userId);
}
package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    /**
     * 查询用户、所有角色
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryAllRoles(Integer userId);

    /**
     * 添加角色：角色名唯一
     * @param roleName
     * @return
     */
    Role queryRoleByRoleName(String roleName);
}
package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.Permission;

import java.util.List;

/**
 * @author NeXT
 */
public interface PermissionMapper extends BaseMapper<Permission,Integer> {
    /**
     * 添加角色授权
     * @param roleId
     * @return
     */
    int countPermissionByRoleId(Integer roleId);

    /**
     * 删除原始的角色记录
     * @param roleId
     * @return
     */
    int deletePermissionByRoleId(Integer roleId);

    /**
     * 根据roleID查询已经存在的角色Id
     * @param roleId
     * @return
     */
    List<Integer> queryRoleHasAllMids(Integer roleId);
}
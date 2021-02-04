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

    /**
     * 角色授权
     *      菜单显示控制：根据用户id查询角色id，再根据角色id查询菜单id
     * @param userId
     * @return
     */
    List<String> queryUserIdHasRoleIdHasModuleId(Integer userId);

    /**
     * 系统设置-菜单管理：菜单删除（拥有菜单的总数量，包含父菜单下的所有子菜单）
     * @param mid
     * @return
     */
    Integer  countPermissionByModuleId(Integer mid);

    /**
     * 系统设置-菜单管理：菜单删除
     * @param mid
     * @return
     */
    int  deletePermissionByModuleId(Integer mid);
}
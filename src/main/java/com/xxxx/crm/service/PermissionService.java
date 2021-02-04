package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author arthur
 * @date 2021/2/4 10:10
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 角色授权
     *      菜单显示控制：根据用户id查询角色id，再根据角色id查询菜单id
     * @param userId
     * @return
     */
    public List<String> queryUserIdHasRoleIdHasModuleId(Integer userId) {
        return permissionMapper.queryUserIdHasRoleIdHasModuleId(userId);
    }
}

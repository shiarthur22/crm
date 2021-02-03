package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.dao.RoleMapper;
import com.xxxx.crm.dao.UserRoleMapper;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Permission;
import com.xxxx.crm.vo.Role;
import com.xxxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 角色管理
 * @author arthur
 * @date 2021/2/2 17:55
 */
@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    /**
     * 查询所有的角色
     * @param userId
     * @return
     */
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }

    /**
     * 角色管理
     *      多条件查询
     * @param RoleQuery
     * @return
     */
    public Map<String,Object> queryRoleByParams(RoleQuery RoleQuery){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(RoleQuery.getPage(),RoleQuery.getLimit());
        // 根据条件查询信息，如果为空，在查询所有
        PageInfo<Role> pageInfo = new PageInfo<Role>(selectByParams(RoleQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    /**
     * 角色管理
     *      角色添加
     *      1. 参数校验
     *          角色名非空
     *      2. 设置默认参数
     *          创建时间、更新时间、角色备注
     *          isValid/createDate/updateDate
     * @param role
     */
    public void saveRole(Role role){
        // 参数校验
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名");
        AssertUtil.isTrue(null != roleMapper.queryRoleByRoleName(role.getRoleName()),"角色名已存在");
        // 设置默认值
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(role)<1,"角色记录添加失败");
    }

    /**
     * 角色管理
     *      角色添加
     *      1. 参数校验
     *          角色名非空
     *      2. 设置默认参数：updateDate
     * @param role
     */
    public void updateRole(Role role){
        // 参数校验
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(role.getId())),"该角色已存在");
        // 设置默认参数
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"角色记录更新失败");
    }

    /**
     * 删除角色
     *      1.参数校验
     *          非空  记录必须存在
     *      2.查询用户角色表记录
     *          如果存在子表记录  删除子表记录
     *      3.执行角色删除操作 判断结果
     * @param roleId
     */
    public void deleteRole(Integer roleId){
        Role role = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null == role,"待删除记录不存在");
        int total = userRoleMapper.countUserRoleByRoleId(roleId);
        if (total>0){
            // 级联删除
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByRoleId(roleId)!=total,"删除角色失败");
        }
        role.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"角色记录删除失败");
    }

    /**
     * 角色授权
     *      核心表：t_permission
     *      角色存在原始授权时，先删除原始授权记录，然后批量添加新的角色权限
     * @param mids
     * @param roleId
     */
    public void addGrant(Integer[] mids, Integer roleId) {
        int total = permissionMapper.countPermissionByRoleId(roleId);
        if (total>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId)!=total,"角色授权失败");
        }
        if (null != mids && mids.length>0){
            List<Permission> permissions = new ArrayList<>();
            for (Integer mid:mids){
                Permission permission = new Permission();
                permission.setCreateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setUpdateDate(new Date());
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissions)!=permissions.size(),"角色授权失败");
        }
    }
}

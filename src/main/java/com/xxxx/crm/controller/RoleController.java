package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.vo.Module;
import com.xxxx.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author arthur
 * @date 2021/2/2 18:00
 */
@Controller()
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    /**
     * 查询所有的用户角色
     * @param userId
     * @return
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles (Integer userId){
        return roleService.queryAllRoles(userId);
    }

    /**
     * 角色管理视图
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    /**
     * 角色管理
     *      条件搜索
     * @param roleQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryRoleByParams(RoleQuery roleQuery){
        return roleService.queryRoleByParams(roleQuery);
    }

    /**
     * 角色管理
     *      添加角色记录
     * @param role
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveRole(Role role){
        roleService.saveRole(role);
        return success("角色添加成功");
    }

    /**
     * 角色管理
     *      更新角色
     * @param role
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色更新成功");
    }

    /**
     * 角色添加和更新视图
     * @return
     */
    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id, Model model){
        model.addAttribute("role",roleService.selectByPrimaryKey(id));
        return "role/add_update";
    }

    /**
     * 角色管理
     *      角色记录删除失败
     * @param roleId
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId){
        roleService.deleteRole(roleId);
        return success("角色记录删除成功");
    }

    /**
     * 角色授权视图
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 给角色添加菜单栏的权限（屁注释，操）
     * @param mids
     * @param roleId
     * @return
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mids,Integer roleId){
        roleService.addGrant(mids,roleId);
        return success("角色授权成功");
    }
}

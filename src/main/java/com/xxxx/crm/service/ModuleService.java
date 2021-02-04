package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.model.TreeModule;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Module;
import com.xxxx.crm.vo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 授权菜单
 * @author arthur
 * @date 2021/2/3 18:29
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 获取授权菜单模型
     * @param roleId
     * @return
     */
    public List<TreeModule> queryAllModules(Integer roleId){
        List<TreeModule> treeModules = moduleMapper.queryAllModules();
        // 查询角色已分配的菜单id
        List<Integer> mids = permissionMapper.queryRoleHasAllMids(roleId);
        if(null != mids && mids.size()>0){
            treeModules.forEach(t->{
                //如果TreeModule数据模型中的id中包含在mids中在设置数据模型中的checked为true
                if (mids.contains(t.getId())){
                    t.setChecked(true);
                }
            });
        }
        return treeModules;
    }

    /**
     * 系统设置：获取菜单
     * @return
     */
    public Map<String, Object> queryModules() {
        Map<String,Object> result = new HashMap<>();
        List<Module> modules = moduleMapper.queryModules();
        result.put("count",modules.size());
        result.put("data",modules);
        result.put("code",0);
        result.put("msg","");
        return  result;
    }

    /**
     * 系统设置-菜单管理：添加目录
     * 1.参数校验
     *     菜单名:非空 同一层级 菜单名唯一；url：级菜单时 非空 不可重复
     *     上级菜单 parentId；一级菜单  parentId (-1)；二级|三级菜单   parentId 非空 上级菜单记录必须存在
     *     菜单层级  grade非空  0|1|2；权限码  optValue非空  不可重复
     * 2.参数默认值设置：isValid   createDate  updateDate
     * 3.执行添加 判断结果
     * @param module
     */
    public void saveModule(Module module) {
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"请输入菜单名");
        Integer grade = module.getGrade();
        // 菜单名:非空 同一层级 菜单名唯一；url：级菜单时 非空 不可重复
        AssertUtil.isTrue(null == grade || !(grade==0||grade==1||grade==3),"菜单层级非法");
        Module module1 = moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName());
        AssertUtil.isTrue(null !=module1,"该层级下菜单名已存在!");
        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请输入二级菜单url地址!");
            module1=moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl());
            AssertUtil.isTrue(null !=module1,"二级菜单下url不可重复!");
        }

        // 二级 三级菜单 必须指定上级菜单id
        if(grade!=0){
            AssertUtil.isTrue(null==module.getParentId() ||null== selectByPrimaryKey(module.getParentId()),"请指定上级菜单!");
        }

        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入菜单权限码!");
        module1 = moduleMapper.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(null !=module1,"权限码重复!");
        // 默认设置
        module.setIsValid((byte)1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        // 执行添加
        AssertUtil.isTrue(insertSelective(module)<1,"菜单添加失败!");
    }

    /**
     *系统设置-菜单管理：菜单更新
     * 1.参数校验
     *    id 记录必须存在
     *     菜单名
     *         非空 同一层级 菜单名唯一
     *     url
     *        二级菜单时 非空 不可重复
     *     上级菜单 parentId
     *        二级|三级菜单   parentId 非空 上级菜单记录必须存在
     *     菜单层级  grade
     *       非空  0|1|2
     *     权限码  optValue
     *        非空  不可重复
     * 2.参数默认值设置
     *      updateDate
     * 3.执行更新 判断结果
     * @param module
     */
    public void updateModule(Module module){
        Module temp =selectByPrimaryKey(module.getId());
        AssertUtil.isTrue(null ==temp,"待修改的菜单记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"请输入菜单名!");
        Integer grade =module.getGrade();
        AssertUtil.isTrue(null==grade || !(grade==0||grade==1||grade==2),"菜单层级非法!");
        temp  = moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(module.getId())),"该层级下菜单名已存在!");

        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请输入二级菜单url地址!");
            temp=moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl());
            AssertUtil.isTrue(null !=temp && !(temp.getId().equals(module.getId())),"二级菜单下url不可重复!");
        }

        // 二级 三级菜单 必须指定上级菜单id
        if(grade!=0){
            AssertUtil.isTrue(null==module.getParentId() ||null== selectByPrimaryKey(module.getParentId()),"请指定上级菜单!");
        }

        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入菜单权限码!");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(module.getId())),"权限码重复!");

        module.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(module)<1,"菜单记录更新失败!");
    }

    /**
     * 系统设置-菜单管理：菜单删除
     * 1.记录必须存在
     *     id 非空  记录存在
     * 2.如果待删除的菜单存在子菜单  不允许直接删除当前菜单
     * 3.如果删除的菜单 在权限表中存在对应记录  此时要级联删除权限表对应记录
     * @param mid
     */
    public void deleteModule(Integer mid){
        Module temp =selectByPrimaryKey(mid);
        AssertUtil.isTrue(null==temp,"待删除的记录不存在!");
        Integer total = moduleMapper.countSubModuleByParentId(mid);
        AssertUtil.isTrue(total>0,"存在子菜单，暂不支持删除操作!");
        // 删除权限表对应记录
        total = permissionMapper.countPermissionByModuleId(mid);
        if(total>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionByModuleId(mid) !=total,"菜单记录删除失败!");
        }
        temp.setIsValid((byte)0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"菜单记录删除失败!");
    }
}

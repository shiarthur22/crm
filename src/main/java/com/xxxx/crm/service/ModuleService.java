package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.model.TreeModule;
import com.xxxx.crm.vo.Module;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
}

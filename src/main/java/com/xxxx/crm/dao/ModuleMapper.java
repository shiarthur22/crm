package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.model.TreeModule;
import com.xxxx.crm.vo.Module;

import java.util.List;

/**
 * 角色授权
 * @author NeXT
 */
public interface ModuleMapper extends BaseMapper<Module,Integer> {
    /**
     * 角色授权菜单列表
     * @param roleId
     * @return
     */
    List<TreeModule> queryAllModules();
}
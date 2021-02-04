package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.model.TreeModule;
import com.xxxx.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色授权
 * @author NeXT
 */
public interface ModuleMapper extends BaseMapper<Module,Integer> {
    /**
     * 角色授权菜单列表
     * @return
     */
    List<TreeModule> queryAllModules();

    /**
     * 系统设置：获取菜单
     * @return
     */
    List<Module> queryModules();

    /**
     * 二级菜单是否合法
     * @param grade
     * @param moduleName
     * @return
     */
    Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);

    /**
     * url校验
     * @param grade
     * @param url
     * @return
     */
    Module queryModuleByGradeAndUrl(@Param("grade") Integer grade,@Param("url") String url);

    /**
     * 权限校验
     * @param optValue
     * @return
     */
    Module queryModuleByOptValue(String optValue);

    /**
     * 系统设置—菜单管理：菜单删除
     * @param parentId
     * @return
     */
    int  countSubModuleByParentId(Integer parentId);
}
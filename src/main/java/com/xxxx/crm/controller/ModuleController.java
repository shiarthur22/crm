package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.model.TreeModule;
import com.xxxx.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author arthur
 * @date 2021/2/3 18:33
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;

    /**
     * 授权树形菜单
     * @param roleId
     * @return
     */
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModule> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }
}

package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.service.CusDevPlanService;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.vo.CusDevPlan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 客户开发计划
 * @author arthur
 * @date 2021/1/27 16:01
 */
@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public String index(){
        return "cusDevPlan/cus_dev_plan";
    }

    /**
     * 开发/详情：转发界面
     * @param sid
     * @param model
     * @return
     */
    @RequestMapping("toCusDevPlanDataPage")
    public String toCusDevPlanDataPage(Integer sid, Model model){
        model.addAttribute("saleChance",saleChanceService.selectByPrimaryKey(sid));
        return "cusDevPlan/cus_dev_plan_data";
    }

    /**
     * 转发跳转到指定页面
     * @param sid
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateCusDevPlanPage")
    public String addOrUpdateCusDevPlanPage(Integer sid,Integer id,Model model){
        model.addAttribute("sid",sid);
        model.addAttribute("cusDevPlan",cusDevPlanService.selectByPrimaryKey(id));
        return "cusDevPlan/add_update";
    }

    /**
     * 客户开发计划
     * @param cusDevPlanQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryCusDevPlansByParams(cusDevPlanQuery);
    }

    /**
     * 计划项记录添加
     * @param cusDevPlan
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划项数据添加成功");
    }

    /**
     * 计划项记录更新
     * @param cusDevPlan
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项数据更新成功");
    }

    /**
     * 计划项记录删除
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("计划项数据删除成功");
    }
}

package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.service.CusDevPlanService;
import com.xxxx.crm.service.SaleChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
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
     * 
     * @param cusDevPlanQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryCusDevPlansByParams(cusDevPlanQuery);
    }
}

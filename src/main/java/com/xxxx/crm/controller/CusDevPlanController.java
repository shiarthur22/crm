package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author arthur
 * @date 2021/1/27 16:01
 */
@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @RequestMapping("index")
    public String index(){
        return "cusDevPlan/cus_dev_plan";
    }
}

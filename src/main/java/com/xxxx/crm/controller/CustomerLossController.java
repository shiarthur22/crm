package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.service.CustomerLossService;
import com.xxxx.crm.service.CustomerRepService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/6 12:07
 */
@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;

    @Resource
    private CustomerRepService customerRepService;

    /**
     * 客户流失查询表单
     * @param customerLossQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryCustomerLossByParams(customerLossQuery);
    }

    /*---------------------------视图--------------------------------*/
    
    /**
     * 客户流失视图
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "customerLoss/customer_loss";
    }

    /**
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toCustomerRepPage")
    public String toCustomerRepPage(Integer id, Model model){
        model.addAttribute("customerLoss", customerLossService.selectByPrimaryKey(id));
        return "customerLoss/customer_rep";
    }

    @RequestMapping("addOrUpdateCustomerRepPage")
    public String addOrUpdateCustomerReprPage(Integer id,Integer lossId,Model model){
        model.addAttribute("customerRep",customerRepService.selectByPrimaryKey(id));
        model.addAttribute("lossId",lossId);
        return "customerLoss/customer_rep_add_update";
    }


    @RequestMapping("updateCustomerLossStateById")
    @ResponseBody
    public ResultInfo updateCustomerLossStateById(Integer id, String lossReason){
        customerLossService.updateCustomerLossStateById(id,lossReason);
        return success("客户确认流失成功!");
    }

    
}

package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.CustomerServeQuery;
import com.xxxx.crm.service.CustomerServeService;
import com.xxxx.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/6 17:14
 */
@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {
    @Resource
    private CustomerServeService customerServeService;

    /**
     * 服务创建
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerServerByParams(CustomerServeQuery customerServeQuery){
        return customerServeService.queryCustomerServerByParams(customerServeQuery);
    }

    /**
     * 服务管理-服务创建：添加
     * @param customerServe
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerServe(CustomerServe customerServe){
        customerServeService.saveCustomerServe(customerServe);
        return success("服务添加成功");
    }

    /**
     * 服务创建-更新
     * @param customerServe
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerServe(CustomerServe customerServe){
        customerServeService.updateCustomerServe(customerServe);
        return success("服务更新成功");
    }

    /*---------------------------------------视图-------------------------------*/

    /**
     * 服务管理视图
     * @return
     */
    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if(null !=type){
            if(type==1){
                return "customerServe/customer_serve";
            }else if(type==2){
                return "customerServe/customer_serve_assign";
            }else if(type==3){
                return "customerServe/customer_serve_proce";
            } else if(type==4){
                return "customerServe/customer_serve_feed_back";
            }else if(type==5){
                return "customerServe/customer_serve_archive";
            } else{
                return "";
            }
        }else {
            return "";
        }
    }

    /**
     * 服务创建：添加视图
     * @return
     */
    @RequestMapping("addCustomerServePage")
    public String addCustomerServePage(){
        return "customerServe/customer_serve_add";
    }

    /**
     * 服务创建-服务分配视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addCustomerServeAssignPage")
    public String addCustomerServeAssignPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_assign_add";
    }

    /**
     * 服务创建-服务处理视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addCustomerServeProcePage")
    public String addCustomerServeProcePage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_proce_add";
    }

    /**
     * 服务创建-服务反馈视图
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addCustomerServeBackPage")
    public String addCustomerServeBackPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_feed_back_add";
    }

    
}

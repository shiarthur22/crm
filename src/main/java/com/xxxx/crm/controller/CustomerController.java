package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.service.CustomerOrderService;
import com.xxxx.crm.service.CustomerService;
import com.xxxx.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 客户管理
 * @author arthur
 * @date 2021/2/5 16:00
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController{

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerOrderService customerOrderService;

    /**
     * 客户管理——多条件查询
     * @param customerQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery){
        return customerService.queryCustomerByParams(customerQuery);
    }

    /**
     * 添加客户
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomer(Customer customer){
        customerService.saveCustomer(customer);
        return success("客户添加成功");
    }

    /**
     * 更新客户
     * @param customer
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return success("客户更新成功");
    }

    /**
     * 删除客户记录
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer id){
        customerService.deleteCustomer(id);
        return success("客户记录删除成功");
    }

    /*-----------------------------视图-----------------------------*/
    /**
     * 客户管理
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }

    /**
     * 添加或更新
     * @return
     */
    @RequestMapping("addOrUpdateCustomerPage")
    public String addOrUpdateCustomerPage(Integer id, Model model){
        model.addAttribute("customer",customerService.selectByPrimaryKey(id));
        return "customer/add_update";
    }

    /**
     * 客户管理-订单查看
     * @return
     */
    @RequestMapping("orderInfoPage")
    public String orderInfoPage(Integer cid,Model model){
        model.addAttribute("customer",customerService.selectByPrimaryKey(cid));
        return "customer/customer_order";
    }

    /**
     * 订单详情
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("orderDetailPage")
    public String orderDetailPage(Integer orderId,Model model){
        model.addAttribute("order",customerOrderService.queryCustomerOrderByOrderId(orderId));
        return "customer/customer_order_detail";
    }

    /**
     * 统计报表
     * @param customerQuery
     * @return
     */
    @RequestMapping("queryCustomerContributionByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery){
        return customerService.queryCustomerContributionByParams(customerQuery);
    }

    /**
     * 统计报表-客户构成分析：折线图 （line chart）
     * @return
     */
    @RequestMapping("countCustomerMakeLineChart")
    @ResponseBody
    public Map<String,Object> countCustomerMakeLineChart(){
        return customerService.countCustomerMakeLineChart();
    }

    /**
     * 统计报表-客户构成分析：饼状图 （pie chart）
     * @return
     */
    @RequestMapping("countCustomerMakePieChart")
    @ResponseBody
    public Map<String,Object> countCustomerMakePieChart(){
        return customerService.countCustomerMakePieChart();
    }

}

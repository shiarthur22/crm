package com.xxxx.crm.dao;

import com.xxxx.base.BaseController;
import com.xxxx.crm.query.CustomerOrderQuery;
import com.xxxx.crm.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/5 19:18
 */
@Controller
@RequestMapping("order")
public class CustomerOrderController extends BaseController {
    @Resource
    private CustomerOrderService customerOrderService;

    /**
     * 订单查看
     * @param customerOrderQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerOrdersByParams(CustomerOrderQuery customerOrderQuery){
        return customerOrderService.queryCustomerOrdersByParams(customerOrderQuery);
    }
}

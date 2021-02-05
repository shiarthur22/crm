package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.query.OrderDetailsQuery;
import com.xxxx.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/5 20:04
 */
@Controller
@RequestMapping("order_details")
public class OrderDetailsController extends BaseController {
    @Resource
    private OrderDetailsService orderDetailsService;

    /**
     * 订单详情-货物
     * @param orderDetailsQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryOrderDerailsByOrderId(OrderDetailsQuery orderDetailsQuery){
        return orderDetailsService.queryOrderDerailsByOrderId(orderDetailsQuery);
    }

}

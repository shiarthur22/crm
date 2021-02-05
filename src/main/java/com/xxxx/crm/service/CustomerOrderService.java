package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CustomerOrderMapper;
import com.xxxx.crm.query.CustomerOrderQuery;
import com.xxxx.crm.vo.CustomerOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/5 19:17
 */
@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {
    @Resource
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 订单查看
     * @param customerOrderQuery
     * @return
     */
    public Map<String, Object> queryCustomerOrdersByParams(CustomerOrderQuery customerOrderQuery) {
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerOrderQuery.getPage(),customerOrderQuery.getLimit());
        PageInfo<CustomerOrder> pageInfo=new PageInfo<CustomerOrder>(selectByParams(customerOrderQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    public Map<String,Object> queryCustomerOrderByOrderId(Integer orderId) {
        return customerOrderMapper.queryCustomerOrderByOrderId(orderId);
    }
}

package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.query.OrderDetailsQuery;
import com.xxxx.crm.vo.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/5 20:10
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails,Integer> {
    /**
     * 订单详情-订货物
     * @param orderDetailsQuery
     * @return
     */
    public Map<String, Object> queryOrderDerailsByOrderId(OrderDetailsQuery orderDetailsQuery) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(orderDetailsQuery.getPage(),orderDetailsQuery.getLimit());
        PageInfo<OrderDetails> pageInfo = new PageInfo<>(selectByParams(orderDetailsQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }
}

package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.CustomerOrder;

import java.util.Map;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {
    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Map<String, Object> queryCustomerOrderByOrderId(Integer orderId);

    /**
     * 添加客户流失信息到客户流失表
     * @param id
     * @return
     */
    CustomerOrder queryLastCustomerOrderByCusId(Integer id);
}
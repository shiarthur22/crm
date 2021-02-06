package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    /**
     * 客户管理-添加客户
     * @param cusName
     * @return
     */
    Customer queryCustomerByName(String cusName);

    /**
     * 客户流失信息
     * @return
     */
    List<Customer> queryLossCustomer();

    /**
     * 更新客户流失状态
     * @param lossCusIds
     * @return
     */
    int updateCustomerStateByIds(List<Integer> lossCusIds);
}
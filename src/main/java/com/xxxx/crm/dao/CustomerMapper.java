package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.Customer;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    /**
     * 客户管理-添加客户
     * @param cusName
     * @return
     */
    Customer queryCustomerByName(String cusName);
}
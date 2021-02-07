package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.vo.Customer;

import java.util.List;
import java.util.Map;

/**
 * @author NeXT
 */
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

    /**
     * 统计报表-客户贡献分析
     *  多条件查询
     * @param customerQuery
     * @return
     */
    List<Map<String,Object>> queryCustomerContributionByParams(CustomerQuery customerQuery);

    /**
     * 统计报表-客户构成分析：折线图
     * @return
     */
    List<Map<String, Object>> countCustomerMake();
}
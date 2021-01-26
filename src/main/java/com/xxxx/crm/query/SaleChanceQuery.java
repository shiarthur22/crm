package com.xxxx.crm.query;

import com.xxxx.base.BaseQuery;

/**
 * 营销模块查询模板
 * @author arthur
 * @date 2021/1/25 17:59
 */
public class SaleChanceQuery extends BaseQuery {
    /**
     * customerName：客户名
     * createMan：创建人
     * state：分配状态
     */
    private String customerName;
    private String createMan;
    private String state;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

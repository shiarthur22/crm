package com.xxxx.crm.query;

import com.xxxx.base.BaseQuery;

/**
 * 客户管理：多条件查询
 * @author arthur
 * @date 2021/2/5 15:42
 */
public class CustomerQuery extends BaseQuery {
    private String CusName;
    private String cusNo;
    private String level;

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

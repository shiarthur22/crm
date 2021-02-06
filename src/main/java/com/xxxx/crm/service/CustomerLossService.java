package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CustomerLossMapper;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerLoss;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户流失
 * @author arthur
 * @date 2021/2/6 12:18
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {
    @Resource
    private CustomerLossMapper customerLossMapper;

    /**
     * 客户流失查询表单
     * @param customerLossQuery
     * @return
     */
    public Map<String, Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery) {
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerLossQuery.getPage(),customerLossQuery.getLimit());
        PageInfo<CustomerLoss> pageInfo=new PageInfo<CustomerLoss>(selectByParams(customerLossQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    public void updateCustomerLossStateById(Integer id, String lossReason) {
        CustomerLoss customerLoss =selectByPrimaryKey(id);
        AssertUtil.isTrue(null==customerLoss,"待流失的客户记录不存在!");
        // 确认流失
        customerLoss.setState(1);
        customerLoss.setLossReason(lossReason);
        customerLoss.setConfirmLossTime(new Date());
        customerLoss.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerLoss)<1,"确认流失失败!");
    }
}

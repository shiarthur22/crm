package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.vo.CusDevPlan;

import java.util.HashMap;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/1/28 12:19
 */
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    /**
     * 客户开发计划
     * @param cusDevPlanQuery
     * @return
     */
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        PageInfo<CusDevPlan> pageInfo=new PageInfo<CusDevPlan>(selectByParams(cusDevPlanQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }
}

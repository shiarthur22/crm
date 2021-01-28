package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CusDevPlanMapper;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/1/28 12:19
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    /**
     * 客户开发计划
     *      获取所有数据
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

    /**
     * 添加计划项
     * 1.参数校验
     *    机会id 非空 记录必须存在
     *    计划项内容非空
     *    计划项时间非空
     * 2. 参数默认值
     *    is_valid  1
     *    createDate 系统时间
     *    updateDate  系统时间
     * 3.执行添加 判断结果
     * @param cusDevPlan
     */
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        // 1.参数校验机会:id 非空、记录必须存在、计划项内容非空、计划项时间非空
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        // 2.参数默认值, is_valid  1、createDate 系统时间、updateDate  系统时间
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        // 3.执行添加 判断结果
        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"计划项记录添加失败");
    }

    /**
     * 更新计划项
     * 1.参数校验
     *    id 记录必须存在
     *    机会id 非空 记录必须存在
     *    计划项内容非空
     *    计划项时间非空
     * 2. 参数默认值
     *    updateDate  系统时间
     * 3.执行更新 判断结果
     * @param cusDevPlan
     */
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        //判断ID是否存在
        AssertUtil.isTrue(null==cusDevPlan.getId() || null==selectByPrimaryKey(cusDevPlan.getId()),"待更新记录不存在");
        // 1.参数校验
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        // 2.默认值
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"待更新记录不存在");
    }

    /**
     * 删除计划项记录
     * @param id
     */
    public void  deleteCusDevPlan(Integer id){
        CusDevPlan cusDevPlan = selectByPrimaryKey(id);
        // 待删除记录是否存在
        AssertUtil.isTrue(null==cusDevPlan,"待删除记录不存在");
        // 假删除，将is-valid设置为0，不会显示
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"记录删除失败");
    }

    /**
     * 参数校验
     * @param saleChanceId
     * @param planItem
     * @param planDate
     */
    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        // id 非空、记录必须存在
        AssertUtil.isTrue(null == saleChanceId || null == selectByPrimaryKey(saleChanceId),"请设置营销机会id");
        // 计划项内容非空
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容");
        // 计划项时间非空
        AssertUtil.isTrue(null == planDate,"请指定计划项日期");
    }


}

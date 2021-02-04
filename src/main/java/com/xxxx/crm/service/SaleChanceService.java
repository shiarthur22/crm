package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.enums.DevResult;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/1/25 18:17
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 营销机会管理：多条件查询
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        // 根据条件查询信息，如果为空，在查询所有
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    /**
     * 营销机会管理添加
     *      1.参数校验
     *          customerName    客户名非空
     *          linkPhone   非空 11为手机号
     *      2.设置相关参数默认值
     *          state：默认未分配     如果选择分配人 state为分配状态
     *          assignTime默认空       如果选择分配人     分配时间为系统当前时间
     *          devResult 默认未开发     如果选择分配人 devResult为开发中 0未开发 1开发中 2开发成功 3开发失败
     *          isValid 默认有效
     *          createDate updateDate默认系统当前时间
     *      3.执行添加 判断添加结果
     * @param saleChance
     */
    public void saveSaleChance(SaleChance saleChance){
        // 1.参数校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        // state：默认未分配
        saleChance.setState(StateStatus.UNSTATE.getType());
        // devResult 默认未开发
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        if (StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        // 验证状态
        saleChance.setIsValid(1);
        // 创建时间
        saleChance.setCreateDate(new Date());
        AssertUtil.isTrue(insertSelective(saleChance)<1,"机会添加失败！");
    }

    /**
     * 1.参数校验
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入客户名！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入手机号");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)),"手机号格式不合法");
    }

    /**
     * 营销机会管理添更新
     * 1.参数校验
     *     id 记录必须存在
     *     customerName  客户名非空
     *     linkMan  非空
     *     linkPhone  非空 11位手机号
     * 2.设置相关参数值
     *     updateDate  系统当前时间
     *       原始记录 未分配 修改后 已分配(分配人是否存在)
     *          state   0--->1
     *          assignTime   设置分配时间 系统时间
     *          devResult  0--->1
     *       原始记录  已分配  修改后  未分配
     *         state 1-->0
     *         assignTime  null
     *         devResult 1-->0
     *  3.执行更新 判断结果
     * @param saleChance
     */
    public void updateSaleChance(SaleChance saleChance){
        SaleChance temp = selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp == null,"待更新记录不存在");
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(temp.getAssignMan())&&StringUtils.isNotBlank(saleChance.getAssignMan()) ){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }else if(StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
            saleChance.setAssignMan("");
        }
        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance)<1,"机会数据更新失败!");
    }

    /**
     * 机会数据批量删除
     * @param ids
     */
    public void deleteSaleChance(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length == 0,"请选择待删除的记录");
        AssertUtil.isTrue(deleteBatch(ids)!= ids.length,"记录删除失败");
    }

    /**
     * 客户开发计划
     *      开发状态设置
     * @param id
     * @param devResult
     */
    public void updateSaleChanceDevResult(Integer id, Integer devResult){
        SaleChance temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == temp,"待更新记录不存在");
        temp.setDevResult(devResult);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"机会数据状态更新失败");
    }
}

package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.query.CustomerRepQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/6 12:45
 */
@Service
public class CustomerRepService extends BaseService<CustomerReprieve,Integer> {

    @Resource
    private CustomerLossService customerLossService;

    /**
     * 添加暂缓
     * @param customerRepQuery
     * @return
     */
    public Map<String, Object> queryCustomerRepByParams(CustomerRepQuery customerRepQuery) {
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerRepQuery.getPage(),customerRepQuery.getLimit());
        PageInfo<CustomerReprieve> pageInfo=new PageInfo<CustomerReprieve>(selectByParams(customerRepQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    /**
     * 添加暂缓
     * 1.参数校验
     *    流失客户id 非空 记录必须存在
     *    暂缓错误  measure 非空
     * 2.参数默认值设置
     *    isValid  createDate updateDate
     * @param customerReprieve
     */
    public void saveCustomerRep(CustomerReprieve customerReprieve){
        AssertUtil.isTrue(null==customerReprieve.getLossId()
                ||null==customerLossService.selectByPrimaryKey(customerReprieve.getLossId()),"请指定流失客户id");
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"请指定措施项!");
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerReprieve)<1,"暂缓措施添加失败!");
    }

    /**
     * 更新暂缓
     * 1.参数校验
     *    id 记录必须存在
     *    流失客户id 非空 记录必须存在
     *    暂缓错误  measure 非空
     * 2.参数默认值设置
     *     updateDate
     * @param customerReprieve
     */
    public void updateCustomerRep(CustomerReprieve customerReprieve){
        AssertUtil.isTrue(null==selectByPrimaryKey(customerReprieve.getId()),"待更新的暂缓措施不存在!");
        AssertUtil.isTrue(null==customerReprieve.getLossId()
                ||null==customerLossService.selectByPrimaryKey(customerReprieve.getLossId()),"请指定流失客户id");
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"请指定措施项!");
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerReprieve)<1,"暂缓措施更新失败!");
    }

    /**
     * 删除暂缓
     * @param id
     */
    public void deleteCustomerRep(Integer id){
        CustomerReprieve temp =selectByPrimaryKey(id);
        AssertUtil.isTrue(null== temp,"待删除的暂缓措施不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"记录删除失败!");
    }


}

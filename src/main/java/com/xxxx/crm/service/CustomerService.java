package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CustomerMapper;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户管理
 * @author arthur
 * @date 2021/2/5 15:46
 */
@Service
public class CustomerService extends BaseService<Customer,Integer> {
    @Resource
    private CustomerMapper customerMapper;

    /**
     * 客户管理：多条件查询
     * @param customerQuery
     * @return
     */
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        PageInfo<Customer> pageInfo = new PageInfo<>(selectByParams(customerQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 添加客户
     * 1.参数校验
     *     客户名称  name 非空 不可重复
     *     phone 联系电话  非空 格式合法
     *     法人  fr 非空
     * 2.参数默认值
     *     isValide
     *     createDatee
     *     updateDate
     * @param customer
     */
    public void saveCustomer(Customer customer){
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        AssertUtil.isTrue(null != customerMapper.queryCustomerByName(customer.getName()),"该客户已存在");
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        // 设置客户编号
        String khno = "KH"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        AssertUtil.isTrue(insertSelective(customer)<1,"客户记录添加失败");
    }

    /**
     * 更新客户
     * 1.参数校验
     *     id 存在性校验
     *     客户名称  name 非空 不可重复
     *     phone 联系电话  非空 格式合法
     *     法人  fr 非空
     * 2.参数默认值
     *     updateDate
     *
     * @param customer
     */
    public void updateCustomer(Customer customer){
        Customer temp = selectByPrimaryKey(customer.getId());
        AssertUtil.isTrue(null == temp,"待更新的记录不存在");
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(customer.getId())),"该客户已存在");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户记录添加失败");
    }

    /**
     * 删除客户记录
     * @param id
     */
    public void deleteCustomer(Integer id){
        Customer customer = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == customer,"待删除的来记录不存在");
        customer.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户记录删除失败");
    }

    /**
     * 参数校验
     * @param name
     * @param phone
     * @param fr
     */
    private void checkParams(String name, String phone, String fr) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"手机号格式不正确");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"请指定公司法人");
    }
}

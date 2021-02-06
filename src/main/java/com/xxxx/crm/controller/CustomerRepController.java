package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.CustomerRepQuery;
import com.xxxx.crm.service.CustomerRepService;
import com.xxxx.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author arthur
 * @date 2021/2/6 12:40
 */
@Controller
@RequestMapping("customer_rep")
public class CustomerRepController extends BaseController {
    @Resource
    private CustomerRepService customerRepService;

    /**
     * 添加暂缓
     * @param customerRepQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerRepByParams(CustomerRepQuery customerRepQuery){
        return customerRepService.queryCustomerRepByParams(customerRepQuery);
    }

    /**
     * 添加暂缓
     * @param customerReprieve
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerRep(CustomerReprieve customerReprieve){
        customerRepService.saveCustomerRep(customerReprieve);
        return  success("暂缓记录添加成功!");
    }

    /**
     * 更新暂缓
     * @param customerReprieve
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerRep(CustomerReprieve customerReprieve){
        customerRepService.updateCustomerRep(customerReprieve);
        return  success("暂缓记录更新成功!");
    }

    /**
     * 删除暂缓
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerRep(Integer id){
        customerRepService.deleteCustomerRep(id);
        return  success("暂缓记录删除成功!");
    }
}

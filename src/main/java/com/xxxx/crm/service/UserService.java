package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.Md5Util;
import com.xxxx.crm.utils.UserIDBase64;
import com.xxxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * service：
 * @author arthur
 * @date 2021/1/22 21:10
 */
@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录参数校验
     *      1.参数校验
     *          用户名  非空
     *          密码    非空
     *      2.根据用户名  查询用户记录
     *      3.用户存在性校验
     *          不存在   -->记录不存在  方法结束
     *      4.用户存在
     *          校验密码
     *          密码错误 -->密码不正确   方法结束
     *      5.密码正确
     *          用户登录成功  返回用户信息
     * @param userName
     * @param userPwd
     * @return
     */
    public UserModel login(String userName,String userPwd){
        // 1.参数校验
        checkParams(userName,userPwd);
        // 2. 查询用户记录
        User user = userMapper.queryUserByUserName(userName);
        // 3.用户存在性校验
        AssertUtil.isTrue(user == null,"用户不存在");
        // 4.用户存在  5.密码正确
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户名或密码不正确");
        return buildUserModelInfo(user);
    }

    /**
     * 获取需要部分属性的洪湖模型
     * @return
     */
    public UserModel buildUserModelInfo(User user){
        UserModel userModel = new UserModel();
        // 将用户的id进行加密，存放在cookie中
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    private void checkParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空");
    }

    /**
     * 修改用户的密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        // 参数校验
        checkParams(userId,oldPassword,newPassword,confirmPassword);
        // 更新密码,得到该更新的用户
        User user = userMapper.selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户密码更新失败！");

    }

    /**
     * 修改用户密码业务
     *      参数校验
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User temp =userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null== userId || null==temp,"用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码!");
        AssertUtil.isTrue(!(temp.getUserPwd().equals(Md5Util.encode(oldPassword))),"原始密码不正确!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码输入不一致!");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码与原始密码不能相同!");
    }
}

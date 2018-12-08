package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IUserService {
    /**
     *注册接口
     *    * @param userInfo
     * @return
     */
    public ServerResponse register(UserInfo userInfo);

    /**
     *登录
     */
    public  ServerResponse login(String username,String password);

    /**
     * 检查邮箱或用户名是否有效
     */
     public ServerResponse check_valid(String str ,String type);

    /**
     * 根据用户获取密保问题
     * @param username
     * @return
     */
    public ServerResponse forget_get_question(String username);

    /**
     * 提交问题答案接口
     */


     ServerResponse forget_get_answer(String username,String question, String answer);

    /**
     * 忘记密码的重置密码
     */
    ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken);

}

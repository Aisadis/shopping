package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements IUserService {

    @Autowired
    UserInfoMapper userInfoMapper ;

    @Override
    public ServerResponse register(UserInfo userInfo) {

        //step1:非空校验
        if(userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),
                    ResponseCode.PARAM_EMPTY.getMsg());
        }

        //step2：判断用户名是否存在
        String username=userInfo.getUsername();
        //int count = userInfoMapper.checkUsername(username);
        /*if (count>0){//用户名已存在
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),
                    ResponseCode.EXISTS_USERNAME.getMsg());
        }*/
        ServerResponse serverResponse = check_valid(username,Const.USERNAME);
        if (!serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }
        //step3:判断邮箱是否已经存在
        /*int result = userInfoMapper.checkEmail(userInfo.getEmail());
          if (result>0){//邮箱存在
              return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),
                      ResponseCode.EXISTS_EMAIL.getMsg());
          }*/
        ServerResponse email_serverResponse = check_valid(userInfo.getEmail(),Const.EMAIL);
        if (!email_serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
        }
        //step4:注册
        userInfo.setRole(Const.USER_ROLE_CUSTOMER);
          userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int insert_result =userInfoMapper.insert(userInfo);
        //step5:返回结果
        if (insert_result>0){
            return ServerResponse.createServerResponseBySuccess("注册成功");
        }
        return ServerResponse.createServerResponseByError("注册失败");

       /*  int count =userInfoMapper.insert(userInfo);
         if (count>0){
             return  ServerResponse.createServerResponseBySuccess();
         }
        return ServerResponse.createServerResponseByError();*/
    }

    @Override
    public ServerResponse login(String username, String password) {

        //step1:参数非空校验

        if (StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByError("用户不能为空");
        }
        if (StringUtils.isBlank(password)){
            return ServerResponse.createServerResponseByError("密码不能为空");
        }
        /*if (username==null || username.equals("")){
            return ServerResponse.createServerResponseByError("用户不能为空");
        }
        if (password==null || password.equals("")){
            return ServerResponse.createServerResponseByError("密码不能为空");
        }*/

        //step2:检查username是否存在
         /*int result= userInfoMapper.checkUsername(username);
         if (result<=0){//用户不存在
             return ServerResponse.createServerResponseByError("用户不存在");
         }*/

         ServerResponse serverResponse = check_valid(username,Const.USERNAME);
         if (serverResponse.isSuccess()){
             return ServerResponse.createServerResponseByError(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
         }

        //step3:根据用户和密码查询
        UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username,MD5Utils.getMD5Code(password));
         if (userInfo==null){//密码错误
             return ServerResponse.createServerResponseByError("密码错误");
         }
        //step4:处理结果并返回
         userInfo.setPassword("");
        return ServerResponse.createServerResponseBySuccess(null,userInfo);
    }

    @Override
    public ServerResponse check_valid(String str, String type) {
     //step1:参数非空校验
        if(StringUtils.isBlank(str) || StringUtils.isBlank(type)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        // step2:校验用户名或邮箱是否存在
        if(type.equals(Const.USERNAME)){
           int username_result =userInfoMapper.checkUsername(str);
           if (username_result>0){
               return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),
                       ResponseCode.EXISTS_USERNAME.getMsg());
           }
           return ServerResponse.createServerResponseBySuccess("成功");
        }else if (type.equals(Const.EMAIL)){
            int email_result =userInfoMapper.checkEmail(str);
            if (email_result>0){
                return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),
                        ResponseCode.EXISTS_EMAIL.getMsg());
            }
            return ServerResponse.createServerResponseBySuccess("成功");
        }
        //step3:返回结果

        return  ServerResponse.createServerResponseByError("type参数传递有误");
    }
}

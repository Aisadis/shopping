package com.neuedu.controller.manger;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/user")
public class UserManageController {



    @Autowired
    IUserService userService;

    /**管理员登陆
     *
     */
    @RequestMapping(value = "login.do")
    public ServerResponse login(HttpSession session, String username, String password){
        ServerResponse serverResponse =  userService.login(username,password);
        if (serverResponse.isSuccess()){//保存登录状态
            UserInfo userInfo = (UserInfo)serverResponse.getData();
            session.setAttribute(Const.CURRENTUSER,serverResponse.getData());
        }
        return serverResponse;
    }

}

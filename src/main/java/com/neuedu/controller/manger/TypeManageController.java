package com.neuedu.controller.manger;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/type")
public class TypeManageController {

     @Autowired
     ITypeService iTypeService;
    /**
     * 获取品类子节点
     * @param session
     * @param
     * @return
     */

    @RequestMapping(value = "/get_type.do")
    public ServerResponse get_type(HttpSession session,Integer typeId){

        UserInfo userInfo=(UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getCode(),Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole()==Const.RolEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return iTypeService.get_type(typeId);
    }

    @RequestMapping(value = "/add_type.do")
    public ServerResponse add_type(HttpSession session,
                                   @RequestParam(required = false,defaultValue = "0")Integer pareentId,
                                   String typeName){
       UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
       if (userInfo==null){
           return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
       }
       if (userInfo.getRole()!=Const.USER_ROLE_ADMIN){
           return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(),ResponseCode.NO_PRIVILEGE.getMsg());
       }
       return iTypeService.add_type(pareentId,typeName);
    }

    @RequestMapping(value = "/set_type_name.do")
    public ServerResponse set_type_name(HttpSession session,Integer typeId,String typeName){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        if (userInfo.getRole()!=Const.USER_ROLE_ADMIN){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(),ResponseCode.NO_PRIVILEGE.getMsg());
        }
        return iTypeService.set_type_name(typeId,typeName);
    }
    @RequestMapping(value = "/get_deep_type.do")
    public ServerResponse get_deep_type(HttpSession session,Integer typeId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        if (userInfo.getRole()!=Const.USER_ROLE_ADMIN){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(),ResponseCode.NO_PRIVILEGE.getMsg());
        }
        return iTypeService.get_deep_type(typeId);
    }

}

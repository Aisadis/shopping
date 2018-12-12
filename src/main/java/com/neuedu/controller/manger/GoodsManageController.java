package com.neuedu.controller.manger;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Goods;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/marage/goods/")
public class GoodsManageController {
    @Autowired
    IGoodsService iGoodsService;

    /**
     * 新增OR更新产品
     **/
    @RequestMapping(value = "save.do")
    public ServerResponse saveOrUpdate(HttpSession session, Goods goods) {
        //判断用户是否登录，并且有管理员权限
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(), ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        if (userInfo.getRole() != Const.USER_ROLE_ADMIN) {
            return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(), ResponseCode.NO_PRIVILEGE.getMsg());
        }
        //调用service层增加或者更新
        return iGoodsService.saveOrUpdate(goods);
    }

    /**
     * 产品的上下架
     **/
    @RequestMapping(value = "set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session, Integer productId,
                                          Integer pstatus) {
        //判断用户是否登录，并且有管理员权限
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(), ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        if (userInfo.getRole() != Const.USER_ROLE_ADMIN) {
            return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(), ResponseCode.NO_PRIVILEGE.getMsg());
        }
        //调用service层增加或者更新
        return iGoodsService.set_sale_status(productId, pstatus);
    }

    @RestController
    @RequestMapping(value = "/product/")
    public class ProductController {

        @Autowired
        IGoodsService iGoodsService;

        /**
         * 前台-商品详情
         */
        @RequestMapping(value = "detail.do")
        public ServerResponse detail(Integer productId) {
            return iGoodsService.detail_portal(productId);
        }

        /**
         * 前台-搜索并排序
         **/
        @RequestMapping(value = "list.do")
        public ServerResponse list(@RequestParam(required = false) Integer typeId,
                                   @RequestParam(required = false) String keyword,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false, defaultValue = "") String orderBy) {

            return iGoodsService.list_portal(typeId, keyword, pageNum, pageSize, orderBy);
        }

        /**
         * 查看商品详情
         **/
        @RequestMapping(value = "detail.do")
        public ServerResponse detail(HttpSession session, Integer goodId) {
            //判断用户是否登录，并且有管理员权限
            UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
            if (userInfo == null) {
                return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(), ResponseCode.USER_NOT_LOGIN.getMsg());
            }
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN) {
                return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(), ResponseCode.NO_PRIVILEGE.getMsg());
            }
            //调用service层增加或者更新
            return iGoodsService.detail(goodId);
        }

        /**
         * 查看商品列表
         **/
        @RequestMapping(value = "list.do")
        public ServerResponse list(HttpSession session,
                                   @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
            //判断用户是否登录，并且有管理员权限
            UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
            if (userInfo == null) {
                return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(), ResponseCode.USER_NOT_LOGIN.getMsg());
            }
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN) {
                return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(), ResponseCode.NO_PRIVILEGE.getMsg());
            }
            //调用service层增加或者更新
            return iGoodsService.list(pageNum, pageSize);
        }

        /**
         * 产品搜索
         **/
        @RequestMapping(value = "search.do")
        public ServerResponse search(HttpSession session,
                                     @RequestParam(value = "productId", required = false) Integer typeId,
                                     @RequestParam(value = "productName", required = false) String goodsName,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
            //判断用户是否登录，并且有管理员权限
            UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
            if (userInfo == null) {
                return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(), ResponseCode.USER_NOT_LOGIN.getMsg());
            }
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN) {
                return ServerResponse.createServerResponseByError(ResponseCode.NO_PRIVILEGE.getStatus(), ResponseCode.NO_PRIVILEGE.getMsg());
            }
            //调用service层增加或者更新
            return iGoodsService.search(typeId, goodsName, pageNum, pageSize);
        }

    }
}

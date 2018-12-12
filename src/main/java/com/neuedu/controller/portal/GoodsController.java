package com.neuedu.controller.portal;


import com.neuedu.common.ServerResponse;
import com.neuedu.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/goods/")

public class GoodsController {
    @Autowired
    IGoodsService iGoodsService;
    /**
     *前台-商品详情
     * */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(Integer goodsId){
        return  iGoodsService.detail_portal(goodsId);
    }
    /**
     * 前台-搜索并排序
     * **/
    @RequestMapping(value = "list.do")
    public ServerResponse list(@RequestParam(required = false) Integer typeId,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false,defaultValue = "") String orderBy){

        return iGoodsService.list_portal(typeId,keyword,pageNum,pageSize,orderBy);
    }

}

package com.neuedu.service.impl;

import com.neuedu.common.ServerResponse;
import com.neuedu.dao.GoodsMapper;
import com.neuedu.dao.TypeMapper;
import com.neuedu.pojo.Goods;
import com.neuedu.service.IGoodsService;
import com.neuedu.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class GoodsSericeImpl implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    ITypeService iTypeService;
    /**
     * 商品添加或更新
     * @param
     * @return
     */
    @Override
    public ServerResponse saveOrUpdate(Goods goods) {

        return null;
    }


    /**
     * 商品上下架
     * @param goodsId
     * @param pstatus
     * @return
     */

    @Override
    public ServerResponse set_sale_status(Integer goodsId, Integer pstatus) {
        return null;
    }

    /**
     * 查看商品详情
     * @param goodsId
     * @return
     */
    @Override
    public ServerResponse detail(Integer goodsId) {
        return null;
    }

    /**
     * 后台-商品列表-分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 后台搜索商品
     * @param goodsId
     * @param goodsName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse search(Integer goodsId, String goodsName, Integer pageNum, Integer pageSize) {
        return null;
    }


    /**
     * 图片上传
     * @param file
     * @param path
     * @return
     */
    @Override
    public ServerResponse upload(MultipartFile file, String path) {
        return null;
    }

    /**
     * 前台-查看商品详情
     * @param goodsId
     * @return
     */
    @Override
    public ServerResponse detail_portal(Integer goodsId) {
        return null;
    }

    /**
     * 前台-搜索
     * @param typeId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public ServerResponse list_portal(Integer typeId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        return null;
    }
}

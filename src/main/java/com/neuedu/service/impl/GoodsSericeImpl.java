package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.GoodsMapper;
import com.neuedu.dao.TypeMapper;
import com.neuedu.pojo.Goods;
import com.neuedu.pojo.Type;
import com.neuedu.service.IGoodsService;
import com.neuedu.service.ITypeService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.PropertiesUtils;
import com.neuedu.vo.GoodsDetailVo;
import com.neuedu.vo.GoodsListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


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
       //step1:参数非空校验
        if (goods==null){
            return ServerResponse.createServerResponseByError("参数为空");
        }
        //step2:主图的构成sub_image-->1.jpg,2.jpg,3.png
        String  subImages = goods.getSubImages();
        //得到一个数组
        if (subImages!=null &&!subImages.equals("")){
            String [] subImageArr=subImages.split(",");
            if (subImageArr.length>0){
                //设置商品主图
                goods.setMainImage(subImageArr[0]);
            }
        }
        //step3:添加或者更新
        if (goods.getId()==null){
            //添加
            int result= goodsMapper.insert(goods);
            if (result>0){
                return ServerResponse.createServerResponseBySuccess("添加成功");
            }else {
                return ServerResponse.createServerResponseByError("添加失败");
            }
        }
        else {
            //更新
            int result = goodsMapper.updateByPrimaryKey(goods);
            if (result>0){
                return ServerResponse.createServerResponseBySuccess("更新成功");
            }else {
                return ServerResponse.createServerResponseByError("更新失败");
            }
        }
    }


    /**
     * 商品上下架
     * @param goodsId
     * @param status
     * @return
     */

    @Override
    public ServerResponse set_sale_status(Integer goodsId, Integer status) {
        //step1:参数非空校验
        if (goodsId==null){
            return ServerResponse.createServerResponseByError("商品id不能为空");
        }
        if (status==null){
            return ServerResponse.createServerResponseByError("商品状态参数不能为空");
        }
        //step2:更新商品状态
        Goods goods= new Goods();
        goods.setId(goodsId);
        goods.setStatus(status);
        int result = goodsMapper.updateGoodsKeySelective(goods);
        //step3:返回结果
        if (result>0){
            return ServerResponse.createServerResponseBySuccess("更新成功");
        }else {
            return  ServerResponse.createServerResponseByError("更新失败");
        }
    }

    /**
     * 查看商品详情
     * @param goodsId
     * @return
     */
    @Override
    public ServerResponse detail(Integer goodsId) {
        //step1:参数非空校验
        if (goodsId==null){
            return ServerResponse.createServerResponseByError("商品id不能为空");
        }
        //step2:查询商品
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods==null){
            return ServerResponse.createServerResponseByError("商品不存在");

        }
        //step3:商品转换-->goodsDetailVo
        GoodsDetailVo goodsDetailVo = assembleGoodsDetailVo(goods);
        //step4:结果返回
        return ServerResponse.createServerResponseBySuccess(null,goodsDetailVo);
    }

    private GoodsDetailVo assembleGoodsDetailVo(Goods goods){
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setTypeId(goods.getTypeId());
        goodsDetailVo.setCreateTime(DateUtil.dateToStr(goods.getCreateTime()));
        goodsDetailVo.setDetail(goods.getDetail());
        //封装域名
        goodsDetailVo.setImageHost(PropertiesUtils.readByKey("imageHost"));
        goodsDetailVo.setName(goods.getName());
        goodsDetailVo.setMainImage(goods.getMainImage());
        goodsDetailVo.setId(goods.getId());
        goodsDetailVo.setPrice(goods.getPrice());
        goodsDetailVo.setStatus(goods.getStatus());
        goodsDetailVo.setStock(goods.getStock());
        goodsDetailVo.setSubImages(goods.getSubImages());
        goodsDetailVo.setSubtitle(goods.getSubtitle());
        goodsDetailVo.setUpdateTime(DateUtil.dateToStr(goods.getUpdateTime()));
        Type type = typeMapper.selectByPrimaryKey(goods.getTypeId());
        if (type!=null){
            goodsDetailVo.setParentTypeId(type.getParentId());
        }else {
            //默认根节点
            goodsDetailVo.setParentTypeId(0);
        }
        return goodsDetailVo;
    }



    /**
     * 后台-商品列表-分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
//查询全部之前调用分页 不可调换 否则分页失效
        //原因是插件实现的原理是springAOP 在SQL语句执行之前加上分页的代码
        PageHelper.startPage(pageNum,pageSize);
        //step1:查询商品数据 默认值不需判断
        List<Goods> goodsList = goodsMapper.selectAll();
        List<GoodsListVo> goodsListVoList= Lists.newArrayList();
        if (goodsList!=null &&goodsList.size()>0){
            for (Goods goods : goodsList) {
                GoodsListVo goodsListVo = assembleGoodsListVo(goods);
                goodsListVoList.add(goodsListVo);
            }
        }
        //PageInfo  代表返回的对象返回到前端
        PageInfo pageInfo = new PageInfo(goodsListVoList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);

    }
    private GoodsListVo assembleGoodsListVo(Goods goods){
        GoodsListVo goodsListVo = new GoodsListVo();
        goodsListVo.setId(goods.getId());
        goodsListVo.setTypeId(goods.getTypeId());
        goodsListVo.setMainImage(goods.getMainImage());
        goodsListVo.setName(goods.getName());
        goodsListVo.setPrice(goods.getPrice());
        goodsListVo.setStatus(goods.getStatus());
        goodsListVo.setSubtitle(goods.getSubtitle());
        return goodsListVo;
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
      //模糊查询通过id或者name
        PageHelper.startPage(pageNum,pageSize);
        //不等于空和不等于空字符串
        if (goodsName!=null && !goodsName.equals("")){
            goodsName="%"+goodsName+"%";
        }
        List<Goods> goodsList = goodsMapper.findGoodsByGoodsIdAndGoodsName(goodsId,goodsName);
        List<GoodsListVo> goodsListVoList = Lists.newArrayList();
        if (goodsList!=null &&goodsList.size()>0){
            for (Goods goods : goodsList) {
                GoodsListVo goodsListVo = assembleGoodsListVo(goods);
                goodsListVoList.add(goodsListVo);
            }
        }
        PageInfo pageInfo = new PageInfo(goodsListVoList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }


    /**
     * 图片上传
     * @param file
     * @param path
     * @return
     */
    @Override
    public ServerResponse upload(MultipartFile file, String path) {

        //step1:参数非空校验
        if (file==null){
            return ServerResponse.createServerResponseByError("没有图片");
        }
        //step2:
        String originFileName = file.getOriginalFilename();
        //截取图片的扩展名，从.开始会把.去掉
        String exName = originFileName.substring(originFileName.lastIndexOf("."));
        //为图片生成新的唯一的名字
        String newFileName = UUID.randomUUID().toString()+exName;
        File pathFile = new File(path);
        //path如果不存在
        if (!pathFile.exists()){
            //设置可写 并生成目录
            pathFile.setWritable(true);
            pathFile.mkdirs();
        }
        //如果 存在，将文件写到file1目录下面
        File filel = new File(path,newFileName);
        try {
            file.transferTo(filel);
            //上传到图片服务器
            Map<String ,String> map = Maps.newHashMap();
            map.put("uri",newFileName);
            map.put("uri",PropertiesUtils.readByKey("imageHost")+"/"+newFileName);
            return ServerResponse.createServerResponseBySuccess(null,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 前台-查看商品详情
     * @param goodsId
     * @return
     */
    @Override
    public ServerResponse detail_portal(Integer goodsId) {
        //step1:参数非空校验
        if (goodsId==null){
            return  ServerResponse.createServerResponseByError("商品id不能为空");
        }
        //step2:查询商品
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods==null){
            return  ServerResponse.createServerResponseByError("商品不存在");
        }
        //step3；校验商品的状态
        if (goods.getStatus()!=ResponseCode.ProductStatusEnum.PRODUGT_ONLINE.getCode()){
            return ServerResponse.createServerResponseByError("商品下架或者删除");
        }
        //step4:获取goodsDetailVo
        GoodsDetailVo goodsDetailVo= assembleGoodsDetailVo(goods);
        //返回结果
        return ServerResponse.createServerResponseBySuccess(null,goodsDetailVo);

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
        //step1:typeid 与keyWord 不能同时为空
        if (typeId==null &&(keyword==null)|| keyword.equals("")){
            return ServerResponse.createServerResponseByError("参数错误");
        }
        //step2:根据typeid查询
        Set<Integer> integerSet = Sets.newHashSet();
        if (typeId!=null){
            Type type= typeMapper.selectByPrimaryKey(typeId);
            if (type==null &&(keyword==null||keyword.equals(""))){
                //说明没有商品同时也得按照格式返回
                //进行分页
                PageHelper.startPage(pageNum,pageSize);
                List<GoodsListVo> goodsListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(goodsListVoList);
                return ServerResponse.createServerResponseBySuccess(null,pageInfo);
            }
            //typeId!=null 查询类别下所有的子类 用递归 调用typeservice
            ServerResponse serverResponse = iTypeService.get_deep_type(typeId);
            if (serverResponse.isSuccess()){
                //如果serverResponse 成功获取到他的数据getData类别 set无序不重复
                //intergerSet 类别下所有子类
                integerSet = (Set<Integer>)serverResponse.getData();
            }
        }
        //step3：keyword 查询
        if (keyword!=null &&!keyword.equals("")){
            keyword="%"+keyword+"%";
        }
        //orderBy 需要排序 有可能是空字符串不需要排序 否则需要排序
        if (orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {
            //传参的字段名_升序、降序
            String[]orederByArr = orderBy.split("_");
            if (orederByArr.length>1){
                //orderByArr[0]+orderByArr[1] 参数含义：第一个长度，第二个升序还是降序
                PageHelper.startPage(pageNum,pageSize,orederByArr[0]+" "+orederByArr[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }
        //step4:List<Type>-->List<GoodsListVo>
        List<Goods> goodsList = goodsMapper.searchGoods(integerSet,keyword);
        List<GoodsListVo> goodsListVoList = Lists.newArrayList();
         if (goodsList!=null && goodsList.size()>0){
             for (Goods goods : goodsList) {
                 GoodsListVo goodsListVo = assembleGoodsListVo(goods);
                 goodsListVoList.add(goodsListVo);
             }
         }
         //step5:分页
        PageInfo pageInfo = new PageInfo();
         pageInfo.setList(goodsListVoList);
         //step6:返回结果
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }
}

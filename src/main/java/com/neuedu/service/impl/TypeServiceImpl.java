package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.TypeMapper;
import com.neuedu.pojo.Type;
import com.neuedu.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class TypeServiceImpl implements ITypeService {

    @Autowired
    TypeMapper typeMapper;

    /**
     * 获取品类的子节点
     * @param typeId
     * @return
     */
    @Override
    public ServerResponse get_type(Integer typeId ){
        //step1:非空校验
        if (typeId==null){
            return ServerResponse.createServerResponseByError("参数不能为空号");
        }
        //step:根据category查询类别
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (type==null){
            return ServerResponse.createServerResponseByError("查询的类别不存在");
        }

        //step3:查询子类别
         List<Type> typeList=typeMapper.findChildType(typeId);

        //step4:返回结果


        return ServerResponse.createServerResponseBySuccess(null,typeList);
    }

    /**
     * 增加节点
     * @param parentId
     * @param typeName
     * @return
     */
    @Override
    public ServerResponse add_type(Integer parentId, String typeName) {
        //step1:非空校验
        if (typeName==null||typeName.equals("")){
            return ServerResponse.createServerResponseByError("类别名称不能为空");
        }
        //step2:添加节点，插入到数据库中
        Type type = new Type();
        type.setName(typeName);
        type.setParentId(parentId);
        type.setStatus(1);
        int result= typeMapper.insert(type);
        //step3:返回结果
        if (result>0){
            return ServerResponse.createServerResponseBySuccess("添加成功");
        }
        return ServerResponse.createServerResponseByError("添加失败");
    }

    /**
     * 修改节点
     * @param typeId
     * @param typeName
     * @return
     */
    @Override
    public ServerResponse set_type_name(Integer typeId, String typeName) {
        //step1:非空校验
        if (typeId==null||typeId.equals("")){
            return ServerResponse.createServerResponseByError("类别id不能为空");
        }
        if (typeName==null||typeName.equals("")){
            return ServerResponse.createServerResponseByError("类别名不能为空");
        }
        //step2:添加节点，插入到数据库中
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (type==null){
            return ServerResponse.createServerResponseByError("类别名不存在");
        }

        //step3:修改
        type.setName(typeName);
        int result = typeMapper.updateByPrimaryKey(type);
        //step3:返回结果
        if (result>0){
            return ServerResponse.createServerResponseBySuccess("修改成功");
        }

        return ServerResponse.createServerResponseByError("修改失败");
    }

    /**
     * 递归子节点查询
     * 有一个结束的语句
     * @param typeId
     * @return
     */
    @Override
    public ServerResponse get_deep_type(Integer typeId) {
        //step1:非空校验
        if (typeId==null){
            return ServerResponse.createServerResponseByError("参数id不能为空");
        }
        //step2:查询
        Set<Type> typeSet = Sets.newHashSet();
        typeSet = findAllChildType(typeSet,typeId);
        //最后得到的是id 需要遍历
        Set<Integer> integerSet= Sets.newHashSet();
        //遍历set集合
        Iterator<Type> typeIterator = typeSet.iterator();
        while (typeIterator.hasNext()){
            //得到泛型
            Type type = typeIterator.next();
            integerSet.add(type.getId());
        }
        return ServerResponse.createServerResponseBySuccess(null,integerSet);
        //写一个私有的set方法查询typeid下面的所有子节点
        //方法中的
    }
    private Set<Type> findAllChildType(Set<Type> typeSet,Integer typeId){
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (type!=null){
            typeSet.add(type);
        }
        List<Type> typeList = typeMapper.findChildType(typeId);
        if (typeList!=null&&typeList.size()>0){
            for (Type type1 : typeList) {
                findAllChildType(typeSet,type1.getId());
            }
        }
        return typeSet;
    }
}

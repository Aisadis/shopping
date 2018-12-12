package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ITypeService {
    /**
     * 获取品类子节点（平级）
     */

    ServerResponse get_type(Integer typeId);
/**
 * 增加子节点
 */

ServerResponse add_type(Integer parentId,String typeName);

/**
 * 修改节点
 */

ServerResponse set_type_name(Integer typeId,String typeName);
/**
 * 获取当前分类id及递归子节点typeId;
 * 关于递归算法：要有一个结束的条件否则就是死循环
 */

ServerResponse get_deep_type(Integer typeId);
/**
 * 查询商品类别的父类
 */
}

## 电商项目-需求分析
### 电商核心-购买
#### 一、用户模块
 ##### 登录
 ##### 注册
 ##### 忘记密码
 ##### 获取用户信息
 ##### 修改密码
 ##### 退出
#### 二、商品模块
 ###### 后台
 ###### 添加商品
 ##### 修改商品
 ##### 删除商品
 ##### 商品上下架
 ##### 查看商品
 ##### 前台（用户）
  ##### 搜索商品
  ##### 查看商品详情
#### 三、类别模块
 ##### 添加类别
 ##### 修改类别
 ##### 删除类别
 ##### 查看类别
  ###### 查看子类
  ###### 查看后代类别
#### 四、购物车模块
 ##### 添加到购物车
 ##### 修改购物车中某个商品的数量
 ##### 删除购物车商品
 ##### 全选、取消全选
 ##### 单选、取消单选
 ##### 查看购物车商品数量
#### 五、地址模块
 ##### 添加地址
 ##### 修改地址
 ##### 删除地址
 ##### 查看地址
#### 六、订单模块
 ##### 前台
  ###### 下订单
  ###### 订单列表
  ###### 取消订单
 ##### 后台
  ###### 订单列表
  ###### 订单详情
  ###### 发货
#### 七、支付模块
 ##### 支付宝支付
  ###### 支付
  ###### 支付回调
  ###### 查看支付状态
#### 八、线上部署
##### 阿里云部署
##---------20181204-----------
###远程分支的合并Dev分支
 ~~~
  git checkout dev
  git pull origin
  git checkout master
  git merge dev 
  git push origin master
 ~~~
 ## 数据库设计
  ### 创建数据库
    ```
    create database shopping;
    use shopping;
    ```
  ### 用户表
   ```
  create table user(
   `id`        int(11) not null auto_increment comment'用户id',
   `username`  varchar(50) not null comment '用户名',
   `password`  varchar(50) not null comment '用户密码',
   `email`     varchar(50) not null comment '用户邮箱',
   `phone`     varchar(11) not null comment '用户电话',
   `question`  varchar(100) not null comment '密保问题',
   `answer`    varchar(100) not null comment '密保答案',
   `role`      int(4) not null default 0 comment '用户角色 0:普通用户 1:管理员',
   `create_time` datetime   comment '创建时间',
   `update_time` datetime   comment '修改时间',
    PRIMARY KEY(`id`)
    UNIQUE KEY `user_name_index`(`username`)USING BTREE
   )ENGINE=InnoDB  DEFAULT CHARSET=UTF8
   ```
  ### 商品表
  ```
  create table  goods(
  `id`        int(11) not null auto_increment comment'商品id',
  `type_id`   int(11) not null comment '商品所属的类别id，值引用类表的id',
  `name`      varchar(100) not null comment '商品名称',
  `detail`    text   comment '商品详情',
  `subtitle`  varchar(200) comment '商品副标题',
  `main_image`varchar(100) comment '商品主图',
  `sub_images`varchar(200) comment '商品子图',
  `price`     decimal(20,2) not null comment '商品价格，总共20位，小数2位，正数18位',
  `stock`     int(11)  comment '商品库存',
  `status`    int(6)  default 1 comment '商品状态 1:在售 2:下架 3:删除',
  `create_time`datetime   comment '创建时间',
  `update_time`datetime   comment '修改时间',
  PRIMARY KEY(`id`)
  )ENGINE=InnoDB  DEFAULT CHARSET=UTF8
  ```
  ### 类别表
  ```create table type(
   `id`          int(11) not null auto_increment comment'类别id',
   `parent_id`   int(11) not null default 0 comment '父类id',
   `name`        varchar(50) not null comment '类别名称',
   `status`      int(4) default 1 comment '类别状态 1:正常 0:废弃',
   `create_time` datetime   comment '创建时间',
   `update_time` datetime   comment '修改时间',
   PRIMARY KEY(`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=UTF8
   create table type(
   `id`          int(11) not null auto_increment comment'类别id',
   `parent_id`   int(11) not null default 0 comment '父类id',
   `name`        varchar(50) not null comment '类别名称',
   `status`      int(4) default 1 comment '类别状态 1:正常 0:废弃',
   `create_time` datetime   comment '创建时间',
   `update_time` datetime   comment '修改时间',
   PRIMARY KEY(`id`)
   )ENGINE=InnoDB  DEFAULT CHARSET=UTF8
                     id      parent_id
    电子产品   1      1         0
    家电       2      2         1 
    手机       2      3         1 
    电脑       2      4         1 
    相机       2      5         1
    华为手机   3      6         3
    小米手机   3      7         3
    p系列      4      8         6
    mate系列   4      9         6
    
    查询电子产品的商品------>递归           
   ```
  ### 订单表
  ```
   create table orders(
   `id`           int(11) not null auto_increment comment'订单id',
   `order_no`     bigint(20) not null comment '订单编号',
   `user_id`      int(11) not null comment'用户id',
   `payment`      decimal(20,2) not null comment '付款金额，单位元，保留小数2位',
   `payment_type` int(4) not null default 1 comment '支付方式 1:线上支付',
   `status`       int(4) not null comment '订单状态 0-已取消 10-未付款 20-已付款 30-已发货 40-已完成 50-已关闭',
   `address_id`  int(11) not null comment'收货地址id',
   `postage`      int(10) not null default 0 comment'运费', 
   `payment_time` datetime default null comment '已付款时间',
   `send_time`    datetime default null comment '已发货时间',
   `close_time`   datetime default null comment '已关闭时间',
   `end_time`     datetime default null comment '已结束时间',
   `create_time`  datetime default null comment '已创建时间',
   `update_time`  datetime default null comment '更新时间',
   PRIMARY KEY(`id`),
    UNIQUE KEY`order_no_index`(`order_no`)USING BTREE
    )ENGINE=InnoDB  DEFAULT CHARSET=UTF8
  ```
  ### 购物车表
  ```
   create table cat(
   `id`        int(11) not null auto_increment comment'购物车id',
   `user_id`   int(11) not null comment '用户ID',
   `goods_id`  int(11) not null comment '商品id',
   `quantity`  int(11) not null comment '购买数量',
   `checked`   int(4)  default 1 comment '1:选中 0:未选中',
   `create_time` datetime   comment '创建时间',
    `update_time` datetime   comment '修改时间',
   PRIMARY KEY(`id`),
   key `user_id_index`(`user_id`)USING BTREE
   )ENGINE=InnoDB  DEFAULT CHARSET=utf8
   ```
  ### 订单明细表
  ```
  create table order_item(
  `id`           int(11) not null auto_increment comment'订单id',
  `order_no`     bigint(20) not null comment '订单编号',
  `user_id`      int(11) not null comment'用户id',
  `goods_id`     int(11) not null comment'商品id',
  `goods_name`   varchar(100) not null comment '商品名称',  
  `goods_image`  varchar(100) comment '商品主图',
  `current_unit_price` decimal(20,2) not null comment '下单时商品的价格，单位元，保留两位小数',
  `quantity`     int(11) not null comment '购买数量',
  `total_price`  decimal(20,2) not null comment '商品的总价格，单位元，保留2位小数',
  `create_time`  datetime   comment '创建时间',
  `update_time`  datetime   comment '修改时间',
  PRIMARY KEY(`id`),
  key `order_no_index`(`order_no`) USING BTREE,
  key `order_no_user_id_index`(`order_no`,`user_id`) USING BTREE
  )ENGINE=InnoDB  DEFAULT CHARSET=utf8
  ```
  ### 支付表
  ```
  create table payinfo(
  `id`           int(11) not null auto_increment comment'支付id',
  `order_no`     bigint(20) not null comment '订单编号',
  `user_id`      int(11) not null comment'用户id',
  `pay_platform`  int(4) not null default 1 comment '1:支付宝 2:微信',
  `platform_status` varchar(50) comment '支付状态'，
  `platform_number` varchar(100) comment '流水号',
  `create_time`  datetime default null comment '创建时间',
  `update_time`  datetime default null comment '修改时间',
  PRIMARY KEY(`id`)
  )ENGINE=InnoDB  DEFAULT CHARSET=utf8
  ```
  ### 收货地址
  ```
  create table address(
  `id`              int(11) not null auto_increment comment'地址编号',
  `user_id`         int(11) not null comment'用户id',
  `receiver_name`    varchar(20) default null comment '收货姓名',
  `receiver_phone`   varchar(20) default null comment '收货电话',
  `receiver_mobile`  varchar(20) default null comment '收货座机',
  `receiver_province`varchar(20) default null comment '省份',
  `receiver_city`    varchar(20) default null comment '城市',
  `receiver_district`varchar(20) default null comment '区/县',
  `receiver_address` varchar(20) default null comment '详细地址',
  `receiver_zip`     varchar(20) default null comment '邮编',
  `create_time`      datetime default null comment '创建时间',
  `update_time`      datetime default null comment '最后更新时间',
  PRIMARY KEY(`id`)
  )ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
  ```
  ### 项目架构--四层架构
  ```
  视图层
  控制层controller
  业务逻辑层service
    接口和实现类
  dao层
  ```
  #### mybatis-generator 插件
  #### 搭建ssm框架
  
  
  
  
  
  )
# SpringCloud-Alibaba-learning
- springcloud-alibaba 学习demo

#### 1、环境
- springcloud  2021.0.5
- springcloud-alibaba 2021.0.5.0
- springboot 2.7.6
- JDK1.8+

#### 2、模块解释
- data 资源数据，数据脚本、nacos配置以及相关组件说明
- springcloud-nacos-discovery-provider 入门级-提供者
- springcloud-nacos-discovery-consumer 入门级-消费者
- springcloud-nacos-config  入门级-nacos配置中心demo
- springcloud-sleuth  入门级-链路追踪demo
- springcloud-nacos-sentinel 入门级-流程控制，熔断等demo
- springcloud-gencode 代码生成器
- springcloud-gateway  网关，动态路由、sentinel简单流控、api分组流控
- springcloud-common  通用模块
  - springcloud-common-bom common包pom引入
  - springcloud-common-core  核心，通用工具实体类
  - springcloud-common-redis  集成redis缓存
  - springcloud-common-security 权限管理类，拦截器异常处理等
  - springcloud-common-swagger 集成swagger3
  - springcloud-common-mybatis-plus 集成mybatis-plus
  - springcloud-common-seata 集成分布式事务seata
  - springcloud-common-sleuth-zipkin 集成链路追踪
  - springcloud-common-sentinel 集成sentinel流控与熔断等
  - springcloud-common-logstash 集成ELK,elasticsearch和kibana自己安装
  - springcloud-common-prometheus 集成prometheus(普罗米西斯)
  - springcloud-common-skylog 收集skywalking日志
  - springcloud-common-satoken sa-token权限校验
  - springcloud-common-api-encrypt api接口RSA+AES加解密
- springcloud-scheduled  分布式定时任务xxl-job
  - springcloud-scheduled-admin 定时器后台管理
  - springcloud-scheduled-demo  定时器执行器demo
- springcloud-module  业务服务模块，实战demo,集成各种组件
  - springcloud-user 用户模块
  - springcloud-order 订单模块
  - springcloud-mall 商城模块
- springcloud-api   各个module的api调用
  - springcloud-api-bom  api的pom包引入
  - springcloud-api-mall  商城模块api
  - springcloud-api-order  订单模块api
  - springcloud-api-user  用户模块api

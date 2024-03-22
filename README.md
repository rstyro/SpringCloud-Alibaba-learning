# SpringCloud-Alibaba-learning
- springcloud-alibaba 学习demo

#### 1、环境
- springcloud  2021.0.5
- springcloud-alibaba 2021.0.5.0
- springboot 2.7.6
- JDK1.8+

#### 2、模块解释
- springcloud-gateway  网关
- springcloud-common  通用模块
  - springcloud-common-bom common包pom引入
  - springcloud-common-core  核心，通用工具实体类
  - springcloud-common-redis  集成redis缓存
  - springcloud-common-security 权限管理类，拦截器异常处理等
  - springcloud-common-swagger swagger3集成
  - springcloud-common-mybatis-plus 集成mybatis-plus
  - springcloud-common-seata 集成分布式事务seata
- springcloud-gencode 代码生成器
- springcloud-nacos-discovery-provider 入门级-提供者
- springcloud-nacos-discovery-consumer 入门级-消费者
- springcloud-nacos-config  nacos配置中心demo
- springcloud-nacos-sentinel sentinel流程控制，熔断等demo
- springcloud-sleuth  链路追踪demo
- springcloud-scheduled  分布式定时任务xxl-job
- springcloud-module  业务服务模块，测试seata
  - springcloud-user 用户模块
  - springcloud-order 订单模块
  - springcloud-mall 商城模块
- springcloud-api   各个module的api调用
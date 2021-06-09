### 一、什么是Sentinel
Sentinel (分布式系统的流量防卫兵) 是阿里开源的一套用于服务容错的综合性解决方案。它以流量
为切入点, 从流量控制、熔断降级、系统负载保护等多个维度来保护服务的稳定性。

#### 1、Sentinel 具有以下特征:
+ **丰富的应用场景：**
Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景, 例如秒杀（即突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等。
+ **完备的实时监控：**
Sentinel 提供了实时的监控功能。通过控制台可以看到接入应用的单台机器秒级数据, 甚至 500 台以下规模的集群的汇总运行情况。
+ **广泛的开源生态：**
Sentinel 提供开箱即用的与其它开源框架/库的整合模块, 例如与 SpringCloud、Dubbo、gRPC 的整合。只需要引入相应的依赖并进行简单的配置即可快速地接入Sentinel。
+ **完善的 SPI 扩展点：**
Sentinel 提供简单易用、完善的 SPI 扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如定制规则管理、适配动态数据源等。

#### 2、Sentinel 分为两个部分:
+ 核心库（Java 客户端）不依赖任何框架/库,能够运行于所有 Java 运行时环境，同时对 Dubbo / Spring Cloud 等框架也有较好的支持。
+ 控制台（Dashboard）基于 Spring Boot 开发，打包后可以直接运行，不需要额外的 Tomcat 等应用容器。


```
[
    {
        "resource": "/sentinel/sayHi",
        "limitApp": "default",
        "grade": 1,
        "count": 5,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
```

+ resource：资源名，即限流规则的作用对象
+ limitApp：流控针对的调用来源，若为 default 则不区分调用来源
+ grade：限流阈值类型（QPS 或并发线程数）；0代表根据并发数量来限流，1代表根据QPS来进行流量控制
+ count：限流阈值
+ strategy：调用关系限流策略,0表示直接,1表示关联,2表示链路;
+ controlBehavior：流量控制效果（,0表示快速失败,1表示Warm Up,2表示排队等待）
+ clusterMode：是否为集群模式
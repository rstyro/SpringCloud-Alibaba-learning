### 一、网关简介
在一个大型的购物网站中，以微服务架构进行拆分，会分为很多种服务，比如购物车、订单服务、评论服务、库存服务、用户服务等等，服务相互之间调用，那么就会产生很多个链接地址，如果有成百上千个服务之间进行调用，那么维护起来是很麻烦的，所以根据环境需要就产生了服务网关。

所谓的网关：就是指系统的统一入口，它封装了应用程序的内部结构，为客户端提供统一服务，一些与业务本身功能无关的公共逻辑可以在这里实现，诸如认证、鉴权、监控、路由转发等。

### 二、SpringCloud Gateway简介
**功能特性：**
+ 建立在Spring Framework 5，Project Reactor和Spring Boot 2.0之上
+ 能够匹配任何请求属性上的路由。
+ 谓词和过滤器特定于路由。
+ 断路器集成。
+ Spring Cloud DiscoveryClient集成
+ 易于编写的谓词和过滤器
+ 请求速率限制，限流
+ 路径改写
+ 设计优雅，容易扩展

**缺点：**
+ 其实现依赖Netty与WebFlux，不是传统的Servlet编程模型，学习成本高
+ 不能将其部署在Tomcat、Jetty等Servlet容器里，只能打成jar包执行
+ 需要Spring Boot 2.0及以上的版本，才支持

### 三、Gateway核心组成
#### 1、Route
路由(Route) 是 Gateway 中最基本的组件之一，表示一个具体的路由信息载体。由ID、URI、一组Predicate、一组Filter组成，根据Predicate进行匹配转发。:
+ id：路由标识、区别于其他route，没什么实际运用效果
+ uri：路由指向的目的地uri，即客户端请求最终被转发到的微服务
+ order：用于多个route之间的排序，数值越小排序越靠前，匹配优先级越高
+ predicate：断言的作用是进行条件判断，只有断言都返回真，才会真正的执行路由 
+ filter：过滤器用于修改请求和响应信息

#### 2、Predicate
断言（谓语）：路由转发的判断条件，目前SpringCloud Gateway支持多种方式，常见如：Path、Query、Method、Header等，写法必须遵循 key=vlue的形式
一个请求满足多个路由的断言条件时，请求只会被首个成功匹配的路由转发

#### 3、Filter
过滤器是路由转发请求时所经过的过滤逻辑，可用于修改请求、响应内容

#### 4、请求过程
+ Gateway Client向Gateway Server发送请求
+ 请求首先会被HttpWebHandlerAdapter进行提取组装成网关上下文
+ 然后网关的上下文会传递到DispatcherHandler，它负责将请求分发给RoutePredicateHandlerMapping
+ RoutePredicateHandlerMapping负责路由查找，并根据路由断言判断路由是否可用
+ 如果过断言成功，由FilteringWebHandler创建过滤器链并调用
+ 请求会一次经过PreFilter--微服务--PostFilter的方法，最终返回响应

### 四、快速开始
因为用Nacos作为注册中心，所以需要引入`spring-cloud-starter-alibaba-nacos-discovery` 。

#### 1、添加依赖
```
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

#### 2、修改 `application.yml` 配置文件
```
spring:
  application:
    name: myGateway
  profiles:
    active: dev
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        username: nacos
        password: rstyro
#  # 网关配置在nacos中配置
    gateway:
      enabled: true
      discovery:
        locator:
          enabled: true #表明gateway开启服务注册和发现的功能，并且spring cloud gateway自动根据服务发现为每一个服务创建了一个router，这个router将以服务名开头的请求路径转发到对应的服务。
          lower-case-service-id: true #是将请求路径上的服务名配置为小写
      routes:
        - id: provider
          uri: lb://nacos-provider
          predicates:
            - Path=/provider/**
          filters:
            # StripPrefix 数字表示要截断的路径的数量
            - StripPrefix=1

# 日志追踪
logging:
  level:
    org.springframework.cloud.gateway: trace
```

#### 3、添加路由日志追踪
按需将如下包的日志级别设置成 `debug` 或 `trace`。
```
org.springframework.cloud.gateway
org.springframework.http.server.reactive
org.springframework.web.reactive
org.springframework.boot.autoconfigure.web
reactor.netty
redisratelimiter
```
例子：
```
# 日志追踪
logging:
  level:
    org.springframework.cloud.gateway: trace
```

#### 4、跨域配置
配置跨域过滤器CorsWebFilter。如下
```
/**
 * 网关跨域
 */
@Configuration
public class GatewayCorsFilter {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        config.addAllowedMethod("OPTIONS");// 允许提交请求的方法类型，*表示全部允许
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}
```

#### 5、添加启动类
```
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
```

### 五、集成sentinel
集成sentinel流量控制、熔断降级

#### 1、添加依赖
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
</dependency>

<!-- sentinel使用nacos 持久化动态配置 --> 
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>

```

#### 2、添加sentinel配置

```yml
# sentinel 默认配置，可在nacos配置覆盖
spring:
  cloud:
    sentinel:
      #服务启动直接建立心跳连接,访问sentinel 控制台就可以看到服务连接情况，不需要第一次访问应用的某个接口，才连接sentinel
      eager: true
      enabled: true
      transport:
        #控制台地址
        dashboard: localhost:8080
```

#### 3、网关流控 GatewayFlowRule 字段解析
- `resource`：资源名称，可以是网关中的 route 名称或者用户自定义的 API 分组名称。
- `resourceMode`：资源模式，`0`=规则是针对 API Gateway 的 route,`1`=用户在 Sentinel 中定义的 API 分组.默认是 route。
- `grade`：限流阈值类型（QPS 或并发线程数）；0代表根据并发数量来限流，1代表根据QPS来进行流量控制
- `count`：限流阈值
- `intervalSec`：统计时间窗口，单位是秒，默认是 1 秒。
- `controlBehavior`：流量整形的控制效果，同限流规则的 `controlBehavior` 字段，目前支持0=快速失败和2=匀速排队两种模式，默认是快速失败。
- `burst`：应对突发请求时额外允许的请求数目。
- `maxQueueingTimeoutMs`：匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效。
- `paramItem` ：参数限流配置。若不提供，则代表不针对参数进行限流，该网关规则将会被转换成普通流控规则；否则会转换成热点规则。其中的字段：
    - `parseStrategy`：从请求中提取参数的策略，查看类：`SentinelGatewayConstants`,目前支持提取来源 IP（0=`PARAM_PARSE_STRATEGY_CLIENT_IP`）、Host（1=`PARAM_PARSE_STRATEGY_HOST`）、任意 Header（2=`PARAM_PARSE_STRATEGY_HEADER`）和任意 URL 参数（3=`PARAM_PARSE_STRATEGY_URL_PARAM`）四种模式。
    - `fieldName`：若提取策略选择 Header 模式或 URL 参数模式，则需要指定对应的 header 名称或 URL 参数名称。
    - `pattern`：参数值的匹配模式，只有匹配该模式的请求属性值会纳入统计和流控；若为空则统计该请求属性的所有值。（1.6.2 版本开始支持）
    - `matchStrategy`：参数值的匹配策略，查看类：`SentinelGatewayConstants` 目前支持精确匹配（0=`PARAM_MATCH_STRATEGY_EXACT`）、子串匹配（3=`PARAM_MATCH_STRATEGY_CONTAINS`）和[正则匹配](https://so.csdn.net/so/search?q=正则匹配&spm=1001.2101.3001.7020)（2=`PARAM_MATCH_STRATEGY_REGEX`）。（1.6.2 版本开始支持）


#### 4、配置流控
从 1.6.0 版本开始，Sentinel 提供了 Spring Cloud Gateway 的适配模块，可以提供两种资源维度的限流：
- route 维度：即在 Spring 配置文件中配置的路由条目，资源名为对应的 routeId
- 自定义 API 维度：用户可以利用 Sentinel 提供的 API 来自定义一些 API 分组


##### ①、route维度
- 因为集成了nacos直接在nacos即可，如下

```yml
# 在 bootstrap.yml 配置如下
spring:
  cloud:
    sentinel:
      datasource:
        ds1:
          nacos:
            server-addr: 127.0.0.1:8848
            username: nacos
            password: nacos
            data-id: sentinel-geteway
            group-id: DEFAULT_GROUP
            data-type: json
            namespace: 94455b2a-cf66-40b5-819b-bba352aaa4f1
            rule-type: gw-flow
```
- 规则类型rule-type,网关可以选择：gw-flow=route流控，gw-api-group=网关api分组
- sentinel-geteway 配置文件如下：
```json
[
    {
        "resource": "cloud-user",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
```
- 其中`cloud-user`就是网关的routeId,可以配置多个，上面就是简单的网关流控配置，下面来 配置API分组


##### ②、自定义API维度
- 首先我们需要在bootstrap.yml配置如下：

```yaml
spring:
  cloud:
    sentinel:
      datasource:
        # 下面是测试 gateway  sentinel api分组
        group-api:
          nacos:
            server-addr: 127.0.0.1:8848
            username: nacos
            password: nacos
            data-id: sentinel-group-api
            group-id: DEFAULT_GROUP
            data-type: json
            namespace: 94455b2a-cf66-40b5-819b-bba352aaa4f1
            rule-type: gw-api-group
        group-rule:
          nacos:
            server-addr: 127.0.0.1:8848
            username: nacos
            password: nacos
            data-id: sentinel-group-rule
            group-id: DEFAULT_GROUP
            data-type: json
            namespace: 94455b2a-cf66-40b5-819b-bba352aaa4f1
            rule-type: gw-flow
```

- 首先我们需要自己定义api，命名为：sentinel-group-api

```json
[
  {
    "apiName":"all-url-qps",
    "predicateItems":[
      {
        "pattern":"/**",
        "matchStrategy": 1 
      }
    ]
  },
  {
    "apiName":"user-qps",
    "predicateItems":[
      {
        "pattern":"/user/**",
        "matchStrategy": 1
      }
    ]
  }
] 
```
- 如上`all-url-qps`api拦截`/**`所有请求
- `user-qps`只拦截`/user`开头的请求
- API的定义，可查看类：`ApiDefinition`
- apiName随便定义，
- predicateItems断言配置可参看：`ApiPathPredicateItem`,
  - matchStrategy匹配策略：0=精确匹配，1=url前缀匹配，2=正册匹配
- api定义好后，再按上面的route维度定义api流控:sentinel-group-rule

```json
[
{
  "resource": "all-url-qps",
  "resourceMode": 1,
  "controlBehavior": 0,
  "count": 3,
  "intervalSec": 1,
  "grade": 1,
  "limitApp": "default",
  "strategy": 0,
  "paramItem": {
      "parseStrategy": 2,
      "fieldName": "uid"
  }
}
,{
  "resource": "user-qps",
  "resourceMode": 1,
  "controlBehavior": 0,
  "count": 20,
  "intervalSec": 86400,
  "grade": 1,
  "limitApp": "default",
  "strategy": 0,
  "paramItem": {
      "parseStrategy": 2,
      "fieldName": "uid"
  }
}
]
```
- 其中第一个配置，1秒内，3个QPS(方便测试)，热点参数为header的`uid`字段
- 第二个86400秒=1天，每个uid最大只能请求count=20个

#### 5、自定义流控异常
您可以在 `GatewayCallbackManager` 注册回调进行定制：
- `setBlockHandler`：注册函数用于实现自定义的逻辑处理被限流的请求，对应接口为 `BlockRequestHandler`。
- 默认实现为 `DefaultBlockRequestHandler`，当被限流时会返回类似于下面的错误信息：`Blocked by Sentinel: FlowException`。
- 我们可以实现 `BlockRequestHandler`接口

```java
@Slf4j
public class SentinelFallbackHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable e) {
        // 默认流控
        ApiResultEnum apiResultEnum = ApiResultEnum.BLOCKED_FLOW;
        if (e instanceof FlowException) {
            log.error("触发流控，url={}", exchange.getRequest().getPath());
            apiResultEnum = ApiResultEnum.BLOCKED_FLOW;
        } else if (e instanceof DegradeException) {
            log.error("触发熔断降级，url={}", exchange.getRequest().getPath());
            apiResultEnum = ApiResultEnum.BLOCKED_DEGRADE;
        } else if (e instanceof AuthorityException) {
            log.error("请求未授权，url={}", exchange.getRequest().getPath());
            apiResultEnum = ApiResultEnum.BLOCKED_AUTHORITY;
        } else if (e instanceof ParamFlowException) {
            ParamFlowException paramFlowException = (ParamFlowException) e;
            ParamFlowRule rule = paramFlowException.getRule();
            log.error("触发热点参数流控，url={},resourceName={},时间={}ms,阀值={}"
                    , exchange.getRequest().getPath()
                    , paramFlowException.getResourceName()
                    , rule.getDurationInSec(), rule.getCount());
            apiResultEnum = ApiResultEnum.BLOCKED_FLOW;
            // 重置
//            resetFlowLimit(exchange,"user-qps");
        }
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(R.fail(apiResultEnum)));
    }

    /**
     * 重置限流规则,可能有需求，所以这里记录个入口
     * @param exchange     exchange
     * @param resourceName 资源名称
     */
    public void resetFlowLimit(ServerWebExchange exchange, String resourceName) {
//        String resourceName="user-qps";
        //获取已转换的参数限流规则
        List<ParamFlowRule> rules = GatewayRuleManager.getConvertedParamRules(resourceName);
        // 获取资源的参数度量指标，其中包含了该资源相关的令牌桶计数器。
        ParameterMetric metric = ParameterMetricStorage.getParamMetricForResource(resourceName);
        // 获取参数度量指标，则从中获取第一条规则对应的令牌桶计数器（CacheMap），键值对表示各个用户的请求计数。
        CacheMap<Object, AtomicLong> tokenCounters = metric == null ? null : metric.getRuleTokenCounter(rules.get(0));
        if (Objects.nonNull(tokenCounters)) {
            // 令牌桶余量
            AtomicLong oldQps = tokenCounters.get(exchange.getRequest().getHeaders().getFirst(SecurityConst.USER_ID));
            if (Objects.nonNull(oldQps) && oldQps.get() == 0) {
                long oldValue = oldQps.get();
                // 重置请求计数值
                oldQps.compareAndSet(oldValue, (long) rules.get(0).getCount());
                log.info("已重置resourceName={}的流控,count={}",resourceName,rules.get(0).getCount());
            }
        }
    }
}
```

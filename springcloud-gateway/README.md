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


### 五、代码参考
+ Github地址：[https://github.com/rstyro/SpringCloud-Alibaba-learning](https://github.com/rstyro/SpringCloud-Alibaba-learning)
+ Gitee地址：[https://gitee.com/rstyro/SpringCloud-Alibaba-learning](https://gitee.com/rstyro/SpringCloud-Alibaba-learning)
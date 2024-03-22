### Seata实战demo
- 为了演示feign相互调用，这里设置3个模块
- 商城模块 `springcloud-mall`
- 订单模块 `springcloud-order`
- 用户模块 `springcloud-user`


#### 1、seata 模拟下单
- 入口在order模块，分别有3个下单接口，模拟seata的不同处理方式
- 可以访问swagger-ui地址：`http://localhost:8002/swagger-ui/index.html#/order/pay1`
- 通过判断feign请求返回结果来判断是否回滚的方式比较灵活，但是如果feign每个都要判断代码就没那么优雅
  - 也可以在全局异常捕获那里，把http状态码设置为500等失败码，也会触发熔断机制（这样特殊情况可以用吧）
- 配置了熔断切片：`GlobalTransactionalAspect.class`当触发熔断时也会回滚
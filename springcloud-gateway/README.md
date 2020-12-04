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
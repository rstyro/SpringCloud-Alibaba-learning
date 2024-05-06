#### Skywalking是什么
- Apache SkyWalking是一个开源的应用性能监视平台与分布式跟踪系统，特别为微服务架构、云原生以及容器化（Docker、Kubernetes）环境设计。
- 它提供了一套全面的解决方案，包括分布式追踪、服务网格遥测、服务可视化、性能诊断、告警通知以及链路分析等功能，帮助开发者快速定位和解决微服务架构下的性能瓶颈和故障问题。

#### SkyWalking有什么用
- **性能监控与故障诊断：**通过追踪每一次请求的全链路，分析各环节耗时，快速定位性能瓶颈。
- **服务可视化：**提供拓扑图，直观展现服务间的依赖关系，以及服务的健康状况。
- **故障追踪：**当服务出现问题时，可以追溯到问题发生的上下文，包括调用链路、参数等，加快问题定位。
- **告警通知：**配置告警策略，当监测到性能指标异常时自动发送通知。
- **容量规划：**基于历史数据，分析服务负载趋势，辅助容量规划与优化资源分配。

#### 快速开始
- 官网下载地址：https://skywalking.apache.org/downloads/
> 下载地址二：https://archive.apache.org/dist/skywalking/


- 从Skywalking9.0.0之后的版本，agent的相关代码被抽离出skywalking当中，需要自行下载agent
- Skywalking有多个组件，我们只需要下载：`SkyWalking APM`和 `Java Agent`这2个
    - SkyWalking APM 是一个可观测性分析平台和应用程序性能管理系统。
    - Java Agent 是一个Java探针，允许在Java应用程序运行时修改或增强其行为的技术
- APM 包含两个服务oapService，webappService
    - oapService为skywalking的后端服务。需要占用11800、12800端口。
        - 11800端口用于跟agent进行交互，获取agent探针到的数据。
        - 12800端口用户跟UI界面的交互，将agent探针到的数据展示在UI界面。
    - webappService为skywalking的UI服务，默认访问端口为8080，可自行配置
        - `apache-skywalking-apm-bin\webapp\application.yml` 修改配置
        - 访问地址：http://localhost:8080/
- Agent下载地址：https://www.apache.org/dyn/closer.cgi/skywalking/java-agent/9.2.0/apache-skywalking-java-agent-9.2.0.tgz

- 启动应用时，添加参数
```bash
# java_opts
-javaagent:D:\env\skywalking\apache-skywalking-java-agent-9.2.0\skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=cloud-user -Dskywalking.collector.backend_service=localhost:11800

# 示例
java -jar -javaagent:D:\code_env\skywalking\apache-skywalking-java-agent-9.2.0\skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=cloud-gateway -Dskywalking.agent.ignore_suffix=.jpg,.jpeg,.js,.css,.png,.bmp,.gif,.ico,.mp3,.mp4,.html,.svg,ping -Dspring.profiles.active=dev -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 springcloud-gateway.jar
```

###  一、什么是Prometheus？

- Prometheus 是一个开源的系统监控和警报工具，主要用于采集和存储时间序列数据，它主要服务于现代的云原生环境，支持各种基础设施和应用程序的监控。
- 最初在 SoundCloud 构建。自 2012 年成立以来，许多 公司和组织都采用了 Prometheus，该项目有一个非常 活跃的开发人员和用户社区。它现在是一个独立的开源项目 并独立于任何公司进行维护。强调这一点，并澄清 该项目的治理架构，Prometheus 于 2016 年加入云原生计算基金会 作为继 Kubernetes 之后的第二个托管项目。

####  1、特征

Prometheus 的主要功能是：

- **多维度数据模型：**Prometheus 使用一个多维度的数据模型，每个样本数据都有一个时间戳、一个数值和一组标签（labels），这使得它可以高效地进行聚合和查询。

- **Pull-based数据采集：**Prometheus 主动从被监控目标（targets）拉取指标数据，而不是等待目标主动推送。

- **强大的查询语言PromQL：**Prometheus 提供了一种强大的表达式查询语言PromQL，用户可以编写复杂的查询语句来分析和可视化监控数据。

- **持久化存储：**Prometheus 内置了本地存储，可以长期存储历史数据，并支持水平扩展以容纳大量数据。

- **警报管理：**Prometheus 提供了警报规则和警报管理器，可以基于指标触发警报，并通过Alertmanager将警报发送给多种通知渠道。

  

在Spring Cloud项目中，Prometheus 可以发挥重要作用，尤其在微服务架构环境中：

- **服务监控：**通过集成Micrometer库（Spring Boot Actuator默认集成了Micrometer），Spring Cloud应用可以导出Prometheus格式的监控指标，如服务调用次数、响应时间、内存使用情况等。
- **服务网格监控：**在Spring Cloud Gateway、Spring Cloud Kubernetes等场景下，Prometheus可以很好地配合服务网格，监控各个服务实例的性能和状态。
- **集群监控：**Prometheus可以与Kubernetes等容器编排平台无缝集成，监控容器的生命周期、资源使用等。

#### 2、Prometheus有什么用，解决了什么问题
Prometheus主要解决了现代分布式系统监控的以下几个问题：
- **集中化监控：**Prometheus通过统一的监控平台收集和展现各种基础设施和应用的指标数据，实现了全栈监控。
- **灵活查询与可视化：**PromQL允许用户编写复杂的查询表达式，深入了解系统性能和行为，配合Grafana等可视化工具，可以直观呈现监控数据和趋势。
- **自动化告警：**Prometheus具备强大的告警功能，可以设定阈值触发告警，并通过Alertmanager进行告警通知和路由。
- **云原生兼容：**Prometheus很好地契合了Kubernetes等容器编排平台，可以轻松监控容器化服务及其资源使用情况。



#### 3、术语名词解释
- **1、指标（Metrics）： **
  - 指标是Prometheus监控的基本元素，包括但不限于计数器（Counters）、仪表盘（Gauges）、直方图（Histograms）和摘要（Summaries）。它们是度量系统状态和行为的数据点，如CPU利用率、内存使用量、HTTP请求成功率等。

- **2、Exporter： **
  - Exporter是Prometheus生态中的一种组件，负责从各种系统、服务和应用程序中收集指标，并以Prometheus可以抓取的格式暴露出来。例如，Node Exporter用于收集主机系统指标，而Jaeger Tracer Exporter则用于收集分布式追踪数据。

- **3、Pull模型： **
  - Prometheus采用Pull（拉取）模型收集数据，即Prometheus服务器定期主动从配置的目标（Target）处拉取指标数据。

- **4、Pushgateway： **
  - 对于短暂任务或批量作业不适合Pull模型的情况下，可以使用Pushgateway暂时存储指标数据，再由Prometheus拉取。

- **5、Service Discovery： **
  - 服务发现机制使Prometheus能自动发现和管理监控的目标，减少手动配置的工作量。







### 二、Prometheus安装
- Prometheus（普罗米修斯）安装
- Github地址：https://github.com/prometheus/prometheus
- 下载地址：https://prometheus.io/download/
- 下载对应系统的安装包，然后解压即可执行进程,如windows的`prometheus.exe`
  - 默认启动端口是9090，执行之后即可访问：http://localhost:9090/ 前端UI页面
  - 如果想修改启动端口则：`prometheus.exe --web.listen-address=:9091`


#### 1、Prometheus监控组件
- 在Prometheus下载页，可以看到与Prometheus监控系统紧密相关的组件可以下载
- **blackbox_exporter**
  - blackbox_exporter 是一个用于执行黑盒监控的工具，它可以通过HTTP(S)、TCP、ICMP等协议来检测远程服务的可达性、响应时间和性能指标。它可以用来监控任何可网络访问的服务端点，而无需了解服务内部工作原理。
- **consul_exporter**
  - consul_exporter 用于从Consul（一款服务网格和服务发现工具）中提取服务健康状况、节点状态和其他关键指标，并将其转换为Prometheus可读的格式，这样Prometheus就可以收集并分析Consul集群中的服务监控数据。
- **graphite_exporter**
  - graphite_exporter 旨在从Graphite服务器导入指标数据。Graphite是一个广泛使用的时序数据存储和可视化工具，graphite_exporter 将其存储的数据格式转换为 Prometheus 可以抓取的格式，使得Prometheus可以整合Graphite环境下的监控数据。
- **memcached_exporter**
  - memcached_exporter 是针对Memcached内存缓存系统的监控工具，它收集Memcached实例的各种统计信息，并暴露给Prometheus进行监控，如缓存命中率、连接数、缓存使用情况等。
- **mysqld_exporter**
  - mysqld_exporter 是专为MySQL数据库设计的监控组件，它可以收集MySQL服务器的性能指标，比如查询速率、连接数、锁争用情况、缓存效率等，并将这些信息转换为Prometheus可以处理的形式。
- **node_exporter**
  - node_exporter 是用于收集本地机器（即“节点”）硬件和操作系统级别的指标，包括CPU使用率、内存使用情况、磁盘I/O、网络流量等系统级统计信息，是Prometheus监控基础设施的重要组成部分。
- **promlens**
  - promlens 是Prometheus的一个附加工具，主要用于查询数据聚合、可视化和报警规则编写，提供了一种更高级的方式来查询和理解Prometheus中的复杂数据关系。
- **pushgateway**
  - pushgateway 是Prometheus架构中的一个组件，它允许临时性的或批处理作业（例如定时任务）将指标推送到Gateway，而不是由Prometheus主动拉取。这对于不支持长时间存活或无法直接被Prometheus拉取数据的job非常有用。
- **statsd_exporter**
  - statsd_exporter 是一个接收StatsD协议发送的指标数据并将之转换为Prometheus可以抓取的格式的工具。StatsD是一种轻量级的度量传输协议，常用于应用程序中实时发送统计数据。通过statsd_exporter，原本使用StatsD的应用程序可以无缝对接到Prometheus监控体系中


### 三、SpringBoot整合Prometheus
导入依赖

#### 1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

#### 2、修改配置

- 在Java应用的application.properties或application.yml中启用并配置Prometheus端点暴露

```yml
management:
  metrics:
    export:
      prometheus:
        enabled: true
  endpoints:
    web:
      # 默认 /actuator，修改地址，不容易让人猜到
      base-path: /meterIndex
      exposure:
      # 如果暴露所有就 '*' 需要用单引号包起来
        include: health, prometheus
```
- 启动Spring Boot应用后，可以直接访问http://localhost:8080/rateIndex/prometheus（假设你的应用监听在8080端口上），你会看到应用暴露出来的所有符合Prometheus格式的指标数据。
- 为了在监控系统中进行分组和筛选，我们可以加上全局标签,新建一个配置类

```java
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * prometheus 配置
 */
@AutoConfiguration
public class PrometheusConfiguration {

    /**
     * 添加全局标签，便于在监控系统中进行分组和筛选。
     * @param applicationName appName
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }

}
```


**修改Prometheus配置**
- 在Prometheus服务器的配置文件prometheus.yml中，需要添加一个静态配置条目指向Spring Boot应用暴露的Prometheus端点：

```yml
scrape_configs:
  - job_name: "cloud-user"
    scrape_interval: 20s
    scrape_timeout: 20s
#	这里的 /meterIndex 是改过的，原来是/actuator
    metrics_path: "/meterIndex/prometheus"
    static_configs:
      - targets: ["localhost:8001"]
      # 注意labels前面是不带 - 的，意思是 指标添加app="cloud-user"的标签
        labels:
          app: cloud-user
```

- 然后重启Prometheus服务，它将会开始抓取你在Spring Boot应用中定义和暴露的所有指标。
- 这样，你就成功地将Spring Boot应用与Prometheus进行了整合，现在可以通过Prometheus的Web界面或者其他可视化工具查看和分析应用的各项指标数据了。
- 可以访问：http://localhost:9090/targets?search= 查看连接情况



### 四、Grafana可视化Prometheus数据
- Grafana 是一款开源的、功能强大的数据可视化和分析平台，它支持多种数据源，特别适合用来创建交互式的仪表板，用于实时监控和数据分析。Grafana 的主要特点和功能包括：

- **数据源多样性：**Grafana 可以连接多种数据源，包括但不限于 Prometheus、Graphite、InfluxDB、Elasticsearch、MySQL、PostgreSQL、Microsoft SQL Server、MongoDB、Azure Monitor、AWS CloudWatch 等众多数据库和监控系统。

- **可视化仪表板：**用户可以使用 Grafana 创建高度定制化的仪表板，通过丰富的图形组件（如折线图、柱状图、饼图、热力图、地理地图等）来展示数据，这些仪表板可以反映系统性能、业务运营、数据库状态等各种维度的信息。

- **动态查询和警报：**Grafana 内置 PromQL、Elasticsearch 查询 DSL 等多种查询语言的支持，允许用户在Explore（探索）模式下实时查询数据，也可以在仪表板中定义动态数据视图。•Grafana 提供强大的警报功能，用户可以基于数据源中的指标设置警报规则，当满足特定条件时，Grafana 会通过邮件、Slack、PagerDuty等多种渠道发送警报通知。

- **多租户和权限管理：**Grafana 支持多用户登录和角色权限管理，可以设置不同的访问级别，保证数据的安全性和隐私保护。

- **模板和插件：**Grafana 提供了丰富的预设仪表板模板和数据源插件，方便用户快速搭建监控系统，同时也支持自定义插件扩展，增强了灵活性和扩展性。

- **联动与嵌入：**Grafana 支持面板之间的联动功能，即点击一个图表可以触发其他图表过滤或变更视图，此外，仪表板还可以嵌入到其他网页或应用程序中。

- **时间范围与播放：**Grafana 支持灵活的时间范围选择，可以查看实时数据或历史数据，同时还有时间序列播放功能，便于观察数据随时间的变化趋势。

- 总的来说，Grafana 是一套非常实用的数据可视化解决方案，不仅限于 IT 监控领域，也可用于商业智能（BI）和数据分析场景，帮助企业更好地理解和优化其业务表现和技术基础设施。随着时间的发展，Grafana 的功能和易用性都在不断改进和完善，已经成为业界广受欢迎的开源工具之一。


#### 1、Grafana下载安装
- 官网地址：https://grafana.com/
- Github地址：https://github.com/grafana/
- 下载地址：https://grafana.com/grafana/download?pg=graf&plcmt=deploy-box-1&platform=windows

**Linux**
```bash
# Linux 下载解压就行
wget https://dl.grafana.com/enterprise/release/grafana-enterprise-10.4.2.linux-amd64.tar.gz
tar -zxvf grafana-enterprise-10.4.2.linux-amd64.tar.gz
# 进入bin目录允许即可
```

**windwos**
- 下载二进制安装包：https://dl.grafana.com/enterprise/release/grafana-enterprise-10.4.2.windows-amd64.msi
- 或者下载压缩包然后解压：https://dl.grafana.com/enterprise/release/grafana-enterprise-10.4.2.windows-amd64.zip
  - 解压之后执行，bin目录下的grafana-server.exe就行


#### 2、Grafana配置Prometheus
- 启动Grafana之后，浏览器访问：http://localhost:3000/ （默认启动端口3000）
- 然后输入用户名密码，默认都是：`admin`,（输入之后可能会提示你修改新密码）
- 添加数据源，大概在Connections=>Data Sources=>Add new connection，添加Prometheus数据源
- 在`Prometheus server URL:`填入我们的Prometheus链接地址：`http://localhost:9090/`然后拉到最后面添加即可
- 然后就可以进入 Dashboards 页面配置我们的仪表盘了
- 一个个手动创建 panel 比较麻烦。Grafana 社区鼓励用户分享 Dashboard，
- 通过 https://grafana.com/dashboards 网站，可以找到大量可直接使用的 Dashboard 模板。
- Grafana 中所有的 Dashboard 都通过 JSON 进行共享，下载并且导入这些 JSON 文件，就可以直接使用这些已经定义好的 Dashboard，或者通过加载URL或ID。
- 比如我们导入：Dashboards  > Import dashboard，可以通过加载ID：3662（地址：https://grafana.com/grafana/dashboards/3662-prometheus-2-0-overview/）来进行导入
- 我们可以通过Dashboard搜索JVM，然后导入一个我们的jvm可视化。(ID=4701)


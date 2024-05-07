### 一、ELK配置

#### 1、下载ELK及启动
- 下载地址如下
- https://www.elastic.co/cn/downloads/past-releases#elasticsearch
- https://www.elastic.co/cn/downloads/past-releases#logstash
- https://www.elastic.co/cn/downloads/past-releases#kibana
- 本次测试使用的版本是：`7.17.18`
- 本次安装在windows
- elasticsearch和kibana 直接解压进入bin执行启动程序即可
  - 执行`elasticsearch.bat`和`kibana.bat`即可
- logstash需要进入解压目录下的config下新建`logstash.conf`
```conf
input {
    tcp {
        port => 4567
        codec => json_lines
    }
}

# 添加localtime时间转换为东八区时间
filter {
    ruby {
    	# 设置一个自定义字段'localtime'[这个字段可自定义]，
    	# 将logstash自动生成的时间戳中的值加8小时，赋给这个字段
       code => "event.set('localtime', event.get('@timestamp').time.localtime + 8*3600)"
    }
 
}

output{
  elasticsearch { 
     # 改成你elasticsearch的地址
     hosts => ["localhost:9200"] 
     # 用一个项目名称来做索引，app-name 和springboot的日志配置相关。
     index => "%{[app-name]}-%{+YYYY.MM.dd}" 
     
  }
  stdout { codec => rubydebug }
}
```
  - 然后执行：`logstash -f 你的解压目录/config/logstash.conf`.
  - 记得配置环境变量`LS_JAVA_HOME`=你的解压目录下/jdk


#### 2、springboot应用程序引用logstash依赖
```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
  <!-- logstash7.3版本及以上使用的logback-core和logback-classic与springboot2.7.6冲突，所以最高7.2 -->
    <version>7.2</version>
</dependency>
```
- 在日志文件`logback.xml`，配置日志
```xml
<springProperty name="appName" scope="context"  source="spring.application.name"/>

    <!--输出到logstash的appender-->
    <appender name="logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <!--可以访问的logstash日志收集端口-->
        <destination>localhost:4567</destination>
        <encoder charset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder">
            <!-- app-name和logstash配置的name一致 -->
            <customFields>{"app-name":"${appName}"}</customFields>
        </encoder>
    </appender>
```

#### 3、微服务模块引用
- 只需要引用`springcloud-common-logstash`
```xml
<dependency>
    <groupId>top.lrshuai</groupId>
    <artifactId>springcloud-common-logstash</artifactId>
</dependency>
```
- 然后在日志文件`logback.xml`加入logstash配置：`<include resource="logback-logstash.xml" />`即可

#### 4、ES清理过期索引
- 请求过期的索引，正则匹配以cloud-开始，后跟年月日的索引
- 可保留7天或一个月内的索引

```bash
#!/bin/bash

# 定义保留天数,优先使用 $1参数，没有参数默认是 7
DAYS_TO_KEEP=${1:-7}

echo "keep=$DAYS_TO_KEEP"
# 清理多少天内的索引
DAYS_TO_MAX=180

# ES 信息,ip和用户名密码
ip=10.168.2.123
ES_USER=elastic
ES_PASS=elastic

# 初始化正则表达式
INDEX_PATTERNS=""

# 生成匹配过去7天的日期正则表达式
for ((i=$DAYS_TO_KEEP; i<=$DAYS_TO_MAX; i++)); do
    # 计算日期
    DATE=$(date -d "$i days ago" +%Y.%m.%d)
    # 添加到正则表达式，匹配以cloud-开始，后跟年月日的索引
    INDEX_PATTERNS+="^cloud-.*-$DATE|"
done

# 移除最后一个'|'符号
INDEX_PATTERN="${INDEX_PATTERNS%|}"

# 使用curl将索引信息写入临时文件
curl  -u $ES_USER:$ES_PASS -s -X GET "http://$ip:9200/_cat/indices/?h=index" > indices.tmp

# 使用grep和awk匹配并提取索引
MATCHED_INDICES=$(grep -E "$INDEX_PATTERN" indices.tmp | awk '{print $1}')

# 删除临时文件
rm indices.tmp

# 遍历索引并删除
for INDEX in $MATCHED_INDICES; do
    echo -e "Deleting index: $INDEX \n"
    curl -u $ES_USER:$ES_PASS -s -X DELETE "http://$ip:9200/$INDEX"
done

echo -e "\n清理完成"
```

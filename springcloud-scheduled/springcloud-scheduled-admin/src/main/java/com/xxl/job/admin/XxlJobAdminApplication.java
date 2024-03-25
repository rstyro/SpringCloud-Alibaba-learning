package com.xxl.job.admin;

import cn.hutool.core.net.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class XxlJobAdminApplication {

	private static final Logger log = LoggerFactory.getLogger(XxlJobAdminApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(XxlJobAdminApplication.class, args);
		printfUrl(application);
	}

	/**
	 * 打印地址
	 * @param application
	 */
	public static void printfUrl(ConfigurableApplicationContext application){
		Environment env = application.getEnvironment();
		String ip = NetUtil.getLocalhostStr();
		String port = env.getProperty("server.port");
		String property = env.getProperty("server.servlet.context-path");
		String path = property == null ? "" :  property;
		log.info("用户模块启动成功,{}:{}{}", ip,port,path);
	}

}
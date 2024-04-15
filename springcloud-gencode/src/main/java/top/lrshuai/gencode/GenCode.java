package top.lrshuai.gencode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Slf4j
public class GenCode {

    /**
     * 作者
     */
    public static final String AUTHOR = "rstyro";
    /**
     * 父级包名
     */
    public static final String basePackages = "top.lrshuai.gencode";
    /**
     * 数据库链接相关信息
     */
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/cloud_mall?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=GMT%2B8";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(DB_URL, DB_USERNAME, DB_PASSWORD);

    public static void main(String[] args) {

        Map<String,Object> param = new HashMap<>();
        param.put("custName","测试注入参数");

        // 代码生成位置
        String outPath = System.getProperty("user.dir")+"/springcloud-gencode/";

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .enableSpringdoc()
                            .disableOpenDir()
                            .outputDir(outPath+"/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent(basePackages)
                            // 设置父包模块名
                            .moduleName("test")
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml,outPath+"/src/main/resources/mapper/test"));
                })
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))) // 设置需要生成的表名
                            // 设置过滤表前缀
                            .addTablePrefix("t_", "c_")
                            .entityBuilder()
                            .enableLombok()
                            .enableChainModel()
                            .naming(NamingStrategy.underline_to_camel)//数据表映射实体命名策略：默认下划线转驼峰underline_to_camel
                            .columnNaming(NamingStrategy.underline_to_camel)//表字段映射实体属性命名规则：默认null，不指定按照naming执行
                            .idType(IdType.AUTO)//添加全局主键类型
                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class)//开启mapper注解
//                            .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
//                            .enableBaseColumnList()//启用xml文件中的BaseColumnList
                            .controllerBuilder()
                            .enableRestStyle()
                    ;

                })
                .templateEngine(new FreemarkerTemplateEngine())
                // 自定义生成代码的模板
                .templateConfig(config->config.entity("/templates/entity1.java"))
                .injectionConfig(config->config.customMap(param))
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}

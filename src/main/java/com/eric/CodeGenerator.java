package com.eric;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CodeGenerator {

    public static void main(String[] args) throws InterruptedException {
        //用来获取Mybatis-Plus.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String projectPath = "";//System.getProperty("user.dir");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + rb.getString("OutputDir"));
        gc.setOpen(false);
        //是否覆盖已有文件
        gc.setFileOverride(true);

        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor(rb.getString("author"));
        gc.setMapperName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(rb.getString("url"));
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        String parent = rb.getString("parent");
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        pc.setController("controller." + rb.getString("className"));
        pc.setService("service." + rb.getString("className"));
        pc.setServiceImpl("service." + rb.getString("className") + ".impl");
        pc.setEntity("entity." + rb.getString("className"));
        pc.setMapper("dao." + rb.getString("className"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String xmlRoot = rb.getString("OutputDirXml") + "/mapper/";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + xmlRoot + rb.getString("className") + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        /*focList.add(new FileOutConfig("templates/mapper_custom.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + xmlRoot + rb.getString("className") + "/custom/" + tableInfo.getEntityName() + "MapperCustom" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig("templates/mapper_custom.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + rb.getString("OutputDir")
                        + "/"
                        + parent.replace(".", "/")
                        + "/dao/"
                        + rb.getString("className")
                        + "/custom/" + tableInfo.getEntityName()
                        + "MapperCustom"
                        + StringPool.DOT_JAVA;
            }
        });*/
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        String baseEntity = rb.getString("base.entity");
        if (baseEntity != null) {
            strategy.setSuperEntityClass(baseEntity);
        }
        String baseController = rb.getString("base.controller");
        if (baseEntity != null) {
            strategy.setSuperControllerClass(baseController);
        }
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_user", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
        tableFillList.add(new TableFill("update_user", FieldFill.UPDATE));

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(false);
        strategy.setEntityBuilderModel(false);
        strategy.setInclude(rb.getString("tableName").split(","));
        strategy.setTableFillList(tableFillList);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setLogicDeleteFieldName("del_flag");
        strategy.setVersionFieldName("version");
        strategy.setSuperEntityColumns(new String[]{"create_time","create_user","update_time","update_user","version","del_flag"});
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}

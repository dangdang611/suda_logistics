//package com.example.admin.generation;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//
///**
// * <p>
// * 自动代码生成器
// * </p>
// */
//public class MpGenerator {
//
//    /**
//     * <p>
//     * MySQL 代码生成
//     * </p>
//     */
//    public static void main(String[] args) {
//        // 需要构建一个 代码自动生成器 对象
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 配置策略
//        // 1、全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath+"/logistics-system/src/main/java");
//        gc.setAuthor("YY");
//        gc.setOpen(false);//生成之后是否打开资源管理器
//        gc.setFileOverride(false);//是否覆盖
//
//        gc.setServiceName("%sService");
//
//        // 去Service的I前缀
//        gc.setIdType(IdType.ASSIGN_ID);//主键策略，雪花算法
//        gc.setDateType(DateType.ONLY_DATE);
//        gc.setSwagger2(true);
//        mpg.setGlobalConfig(gc);
//
//        //2、设置数据源
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/logistics?serverTimezone=UTC");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("121022gui");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        //3、包的配置
//        PackageConfig pc = new PackageConfig();
//        //只需要改实体类名字 和包名 还有 数据库配置即可
//        pc.setModuleName("sc"); pc.setParent("com.example.system.sc");
//        pc.setEntity("pojo"); pc.setMapper("mapper");
//        pc.setService("service"); pc.setController("controller");
//        mpg.setPackageInfo(pc);
//
//        //4、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("logistics_user_order_path");// 需要生成的类对应的数据库表名，可以在这里写多个
//        // 设置要映射的表名
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setEntityLombokModel(true);
//        // 自动lombok；
//        strategy.setLogicDeleteFieldName("deleted");//设置逻辑删除
//
//        // 自动填充配置
//        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
//        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
//        ArrayList<TableFill> tableFills = new ArrayList<>();
//        tableFills.add(gmtCreate); tableFills.add(gmtModified);
//        strategy.setTableFillList(tableFills);
//
//        // 乐观锁
//        strategy.setVersionFieldName("version");
//        strategy.setRestControllerStyle(true);
//        strategy.setControllerMappingHyphenStyle(true);
//
//        // localhost:8080/hello_id_2
//        mpg.setStrategy(strategy);
//        mpg.execute(); //执行
//    }
//
//}
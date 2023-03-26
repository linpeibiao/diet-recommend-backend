//package backen_base;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//
///**
// * @author xbx
// * @creat 2022-07-5-16:18
// */
//public class CodeGenerator {
//
//    public static void main(String[] args){
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 2、全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/backen-base/src/main/java");
//
//        gc.setAuthor("xiaohu");
//        gc.setOpen(false); //生成后是否打开资源管理器
//        gc.setFileOverride(true); //重新生成时文件是否覆盖
//
//        //UserService
//        gc.setServiceName("%sService");
//        gc.setIdType(IdType.ASSIGN_ID); //主键策略
//        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
//        gc.setSwagger2(true);//开启Swagger2模式
//
//        mpg.setGlobalConfig(gc);
//
//        // 3、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://119.91.234.58:3306/media_assets?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("linpeibiaoxiaohu");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        // 4、包配置
//        PackageConfig pc = new PackageConfig();
//        //包  com.dove.equipment
//        pc.setParent("icu.xiaohu");
//        pc.setModuleName("backen_base"); //模块名
//        //包  com.dove.equipment.controller
//        pc.setController("controller");
//        pc.setEntity("entity");
//        pc.setService("service");
//        pc.setMapper("mapper");
//        mpg.setPackageInfo(pc);
//
//        // 5、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//
//        strategy.setInclude("t_user");
//
//
//        strategy.setLogicDeleteFieldName("is_deleted");
//            // 自动填充配置
//        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
//        TableFill gmtModified = new TableFill("update_time",
//                    FieldFill.INSERT_UPDATE);
//        ArrayList<TableFill> tableFills = new ArrayList<>();
//        tableFills.add(gmtCreate);
//        tableFills.add(gmtModified);
//        strategy.setTableFillList(tableFills);
//
//        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
//        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
//        strategy.setVersionFieldName("version");// 乐观锁 @Version
//        strategy.setLogicDeleteFieldName("is_deleted"); // 逻辑删除 @TableLogic
//        strategy.setTablePrefix("t_"); //生成实体时去掉表前缀
//        strategy.setEntityBooleanColumnRemoveIsPrefix(false);//去掉布尔值的is_前缀（确保tinyint(1)） 加注解--@TableField("is_deleted")
//        strategy.setRestControllerStyle(true); //restful api风格控制器
//        strategy.setControllerMappingHyphenStyle(false); //url中驼峰-->连字符(true)，连字符(true)-->驼峰
//        mpg.setStrategy(strategy);
//
//
//        // 6、执行
//        mpg.execute();
//    }
//
//}

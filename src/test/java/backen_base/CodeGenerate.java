package backen_base;
//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.fill.Column;
//import org.junit.Test;
//
///**
// * @author xiaohu
// * @date 2022/07/22/ 14:51
// * @description
// */
//public class CodeGenerate {
//    @Test
//    public void generator(){
//        String projectPath = System.getProperty("user.dir");
//        FastAutoGenerator.create("jdbc:mysql://120.25.178.186:3306/chicken_cloud_slaughter?serverTimezone=GMT%2B8", "root", "feiyilin.")//连接数据库
//                .globalConfig(builder -> {
//                    builder.author("LPB") // 设置作者
//                            //.enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
//                            .outputDir(projectPath+"/src/main/java"); // 指定输出目录
//                })
//                .packageConfig(builder -> {
//                    builder.parent("com.chicken"); // 设置父包名
//                })
//                .strategyConfig(builder -> {
//                    builder.addInclude("t_single_chicken_slaughter")// 设置需要生成的表名
//                            .addInclude("t_basket_slaughter")
//                            .addTablePrefix("t_") // 设置过滤表前缀
//                            .entityBuilder()
//                            .enableLombok()
//                            .versionColumnName("version")
//                            .logicDeleteColumnName("is_deleted")
//                            .logicDeletePropertyName("isDeleted")
//                            .idType(IdType.ASSIGN_ID)
//                            .addTableFills(new Column("创建时间", FieldFill.INSERT))
//                            .addTableFills(new Column("修改时间", FieldFill.INSERT_UPDATE))
//                            .controllerBuilder()
//                            .enableRestStyle();
//                })
//                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();
//    }
//}

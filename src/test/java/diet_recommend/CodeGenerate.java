package diet_recommend;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.junit.Test;

/**
 * @author xiaohu
 * @date 2022/07/22/ 14:51
 * @description
 */
public class CodeGenerate {
    @Test
    public void generator(){
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://43.139.60.130:7706/diet_recommend?serverTimezone=GMT%2B8", "root", "linpeibiaoxiaohu")//连接数据库
                .globalConfig(builder -> {
                    builder.author("xiaohu") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath+"/src/main/java/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("icu.xiaohu.diet_recommend"); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_material", "t_meal","t_meal_material", "t_menu",
                            "t_menu_meal", "t_user_meal")// 设置需要生成的表名
                            .addTablePrefix("t_") // 设置过滤表前缀
                            .entityBuilder()
                            .enableLombok()
                            .versionColumnName("version")
                            .logicDeleteColumnName("is_deleted")
                            .logicDeletePropertyName("isDeleted")
                            .idType(IdType.ASSIGN_ID)
                            .addTableFills(new Column("创建时间", FieldFill.INSERT))
                            .addTableFills(new Column("修改时间", FieldFill.INSERT_UPDATE))
                            .controllerBuilder()
                            .enableRestStyle();
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

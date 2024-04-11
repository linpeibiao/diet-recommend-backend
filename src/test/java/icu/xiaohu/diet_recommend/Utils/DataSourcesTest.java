package icu.xiaohu.diet_recommend.Utils;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/05/02/ 12:01
 * @description
 */
public class DataSourcesTest {

    @Test
    public void getMaterialsFromNetTest() throws Exception{
        int pageNum = 10, pageSize = 1;
        long current = (pageNum - 1) * pageSize;
        String searchText = "猪蹄";
        String url = String.format("https://home.meishichina.com/search/ingredient/%s/page/%s/", searchText, current);
        Document document = Jsoup.connect(url).get();
        Elements ui_list_1 = document.getElementsByClass("ui_list_1");
        List<Material> list = new ArrayList<>();
        for (Element element : ui_list_1) {
            Elements lis = element.getElementsByTag("li");
            for (Element li : lis) {
                Material material = new Material();
                String dishName = li.select("h4").first().text();
                material.setName(dishName);
                String picUrl = "https:" + li.select("img").first().attr("data-src");
                material.setRemark(picUrl);
                String desc = li.select("p").first().text();
                material.setDescription(desc);
                list.add(material);
            }
        }
        System.out.println(list);
    }

    @Test
    public void getMealsFromNetTest() throws Exception{
        int pageNum = 10, pageSize = 1;
        long current = (pageNum - 1) * pageSize;
        String searchText = "猪蹄";
        String url = String.format("https://home.meishichina.com/search/recipe/%s/page/%s/", searchText, current);
        Document document = Jsoup.connect(url).get();
        Elements ui_list_1 = document.getElementsByClass("ui_list_1");
        List<Meal> list = new ArrayList<>();
        for (Element element : ui_list_1) {
            Elements lis = element.getElementsByTag("li");
            for (Element li : lis) {
                Meal meal = new Meal();
                String dishName = li.select("h4").first().text();
                meal.setName(dishName);
                String detail = li.select("h4 > a").first().attr("href");
                String picUrl = "https:" + li.select("img").first().attr("data-src");
                meal.setUrl(picUrl);
                String mainMaterial = li.select("p").first().text().replace("原料", "");
                meal.setMainMaterial(mainMaterial);
                list.add(meal);
            }
        }
    }
}

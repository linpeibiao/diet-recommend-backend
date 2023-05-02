package icu.xiaohu.diet_recommend.recommend.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.constant.SearchType;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/05/02/ 2:28
 * @description
 */
@Service
public class MealDataSource implements DataSource<Meal>{

    @Resource
    private DataSourceRegister dataSourceRegister;

    @PostConstruct
    public void doInit(){
        dataSourceRegister.getTypeDataSourceMap().put(SearchType.MEAL.getType(), this);
    }

    @Override
    public Page<Meal> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://home.meishichina.com/search/recipe/%s/page/%s/", searchText, current);
        Document document = null;
        try{
            document = Jsoup.connect(url).get();
        }catch (IOException e){
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "数据获取异常");
        }
        Elements ui_list_1 = document.getElementsByClass("ui_list_1");
        List<Meal> list = new ArrayList<>();
        for (Element element : ui_list_1) {
            Elements lis = element.getElementsByTag("li");
            for (Element li : lis) {
                Meal meal = new Meal();
                String dishName = li.select("h4").first().text();
                meal.setName(dishName);
                String detail = li.select("h4 > a").first().attr("href");
                meal.setRemark(detail);
                String picUrl = "https:" + li.select("img").first().attr("data-src");
                meal.setUrl(picUrl);
                String mainMaterial = li.select("p").first().text().replace("原料", "");
                meal.setMainMaterial(mainMaterial);
                list.add(meal);
            }
        }
        Page<Meal> mealPage = new Page<>(pageNum, pageSize);
        mealPage.setRecords(list);
        return mealPage;
    }
}

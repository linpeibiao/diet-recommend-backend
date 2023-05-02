package icu.xiaohu.diet_recommend.recommend.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.constant.SearchType;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.entity.Material;
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
public class MaterialDataSource implements DataSource<Material>{

    @Resource
    private DataSourceRegister dataSourceRegister;

    @PostConstruct
    public void doInit(){
        dataSourceRegister.getTypeDataSourceMap().put(SearchType.MATERIAL.getType(), this);
    }

    @Override
    public Page<Material> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://home.meishichina.com/search/ingredient/%s/page/%s/", searchText, current);
        Document document = null;
        try{
            document = Jsoup.connect(url).get();
        }catch (IOException e){
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "数据获取异常");
        }
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
        Page<Material> materialPage = new Page(pageNum, pageSize);
        materialPage.setRecords(list);
        return materialPage;
    }
}

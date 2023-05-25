package icu.xiaohu.diet_recommend.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.dto.request.SearchRequest;
import icu.xiaohu.diet_recommend.model.entity.Material;
import icu.xiaohu.diet_recommend.model.entity.Meal;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.model.vo.SearchVO;
import icu.xiaohu.diet_recommend.recommend.dataSource.DataSource;
import icu.xiaohu.diet_recommend.recommend.dataSource.DataSourceRegister;
import icu.xiaohu.diet_recommend.recommend.dataSource.MaterialDataSource;
import icu.xiaohu.diet_recommend.recommend.dataSource.MealDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author xiaohu
 * @date 2023/05/02/ 2:19
 * @description 饮食数据搜索门面
 */
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private DataSourceRegister dataSourceRegistry;

    @Resource
    private MealDataSource mealDataSource;
    @Resource
    private MaterialDataSource materialDataSource;

    public SearchVO searchAll(SearchRequest searchRequest){
        if (searchRequest == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请求体不能为空");
        }
        String searchText = searchRequest.getSearchText();
        if (StringUtils.isBlank(searchText)){
            return null;
        }
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        String type = searchRequest.getType();
        // 查询类型为空，查询全部
        if (StringUtils.isBlank(type)){
            CompletableFuture<Page<Meal>> getMealTask = CompletableFuture.supplyAsync(() -> {
                Page<Meal> mealPage = mealDataSource.doSearch(searchText, pageSize, current);
                return mealPage;
            });

            CompletableFuture<Page<Material>> getMaterialTask = CompletableFuture.supplyAsync(() -> {
                Page<Material> materialPage = materialDataSource.doSearch(searchText, pageSize, current);
                return materialPage;
            });

            // 开启线程，等待都执行结束后再继续执行一下代码（会有短板效应）
            CompletableFuture.allOf(getMealTask, getMaterialTask).join();
            try {
                Page<Meal> mealPage = getMealTask.get();
                Page<Material> materialPage = getMaterialTask.get();
                SearchVO searchVO = new SearchVO();
                searchVO.setMeals(mealPage.getRecords());
                searchVO.setMaterials(materialPage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ResultCode.SYSTEM_ERROR, "查询异常");
            }
        }

        DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
        Page<?> page = dataSource.doSearch(searchText,pageSize, current);
        SearchVO searchVO = new SearchVO();
        searchVO.setDataList(page.getRecords());
        return searchVO;
    }
}

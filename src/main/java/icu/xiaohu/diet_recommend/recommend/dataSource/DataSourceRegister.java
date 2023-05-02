package icu.xiaohu.diet_recommend.recommend.dataSource;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohu
 * @date 2023/05/02/ 2:24
 * @description 数据源注册中心
 */
@Component
@Data
public class DataSourceRegister<T> {

    private Map<String, DataSource<T>> typeDataSourceMap = new HashMap<>();

    public DataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}

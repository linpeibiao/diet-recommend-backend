package icu.xiaohu.diet_recommend.recommend.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author xiaohu
 * @date 2023/05/02/ 2:21
 * @description 数据来源
 */
public interface DataSource<T> {
    /**
     * 搜索
     * @param searchText 搜索关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}

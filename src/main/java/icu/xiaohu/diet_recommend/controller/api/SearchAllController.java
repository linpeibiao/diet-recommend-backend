package icu.xiaohu.diet_recommend.controller.api;

import icu.xiaohu.diet_recommend.manager.SearchFacade;
import icu.xiaohu.diet_recommend.model.dto.request.SearchRequest;
import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.model.vo.SearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiaohu
 * @date 2023/05/02/ 15:10
 * @description
 */
@RestController
@RequestMapping("/search")
@Api(tags = "饮食数据查询门面")
public class SearchAllController {
    @Resource
    private SearchFacade searchFacade;

    @ApiOperation("查询数据")
    @PostMapping("/all-by")
    public Result<SearchVO> searchAll(@RequestBody SearchRequest searchRequest){
        SearchVO searchVO = searchFacade.searchAll(searchRequest);
        return Result.success(searchVO);
    }
}

package icu.xiaohu.diet_recommend.controller;

import icu.xiaohu.diet_recommend.model.result.Result;
import icu.xiaohu.diet_recommend.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xiaohu
 * @date 2023/04/09/ 12:37
 * @description
 */
@Api(tags = "文件存储服务")
@RestController
@RequestMapping("/file")
public class PictureController {
    @Resource
    private PictureService pictureService;
    /**
     * 上传图片
     * @return
     */
    @ApiOperation("上传图片到服务器")
    @PostMapping(value = "/upload", consumes = "multipart/*", headers="content-type=multipart/form-data")
    public Result<String> upload(@RequestParam(value = "multipartFile" ,required = true) MultipartFile multipartFile, @RequestParam(required = false) String bucketName, @RequestParam(required = false) String objectName){
        String url = pictureService.uploadPictureToMinio(multipartFile, bucketName, objectName);
        return Result.success(url);
    }

    @ApiOperation("获取文件url")
    @PostMapping(value = "/get-url")
    public Result<String> getUrl(@RequestParam String bucketName, @RequestParam String objectName){
        String url = pictureService.getPictureUrl(bucketName, objectName);
        return Result.success(url);
    }


}

package icu.xiaohu.diet_recommend.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaohu
 * @date 2023/04/09/ 12:34
 * @description
 */
public interface PictureService {
    /**
     * 上传图片到文件系统
     * @param file
     * @param bucketName
     * @param objectName
     * @return
     */
    String uploadPictureToMinio(MultipartFile file, String bucketName, String objectName);

    /**
     * 获得图片访问地址
     * @param bucketName
     * @param objectName
     * @return
     */
    String getPictureUrl(String bucketName, String objectName);
}

package icu.xiaohu.diet_recommend.service.impl;

import icu.xiaohu.diet_recommend.exception.BusinessException;
import icu.xiaohu.diet_recommend.model.result.ResultCode;
import icu.xiaohu.diet_recommend.service.PictureService;
import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/04/09/ 12:35
 * @description
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Resource
    private MinioClient minioClient;

    @Value("${minio.url}")
    private String url;
    @Override
    public String uploadPictureToMinio(MultipartFile file, String bucketName, String objectName) {
        try {
            // 执行创建桶
            createBucket(bucketName);
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(objectName)
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),-1).build();

            minioClient.putObject(objectArgs);
            return url + bucketName + "/" + objectName ;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void createBucket(String bucketName) throws Exception {
        BucketExistsArgs.Builder bucket = BucketExistsArgs.builder().bucket(bucketName);
        if (!minioClient.bucketExists(bucket.build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    @Override
    public String getPictureUrl(String bucketName, String objectName) {
        try {
            InputStream object = minioClient.getObject(
                    GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
            if (object == null) {
                throw new BusinessException(ResultCode.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new BusinessException(ResultCode.NOT_FOUND, "未找到对应数据");
        }
        return url + bucketName + "/" + objectName;
    }

    /**
     * 获取文件系统所有资源
     * @return
     */
    public List<String> getAllMediaResources(){
        return null;
    }
}

package com.lingxi.service.impl;

import com.google.gson.Gson;
import com.lingxi.config.QiniuConfig;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.model.param.DeletePic;
import com.lingxi.model.param.DeletePics;
import com.lingxi.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UploadServiceImpl implements UploadService {


    @Autowired
    private QiniuConfig qiniuConfig;

    /**
     * 1.图片上传
     *
     * @param bytes
     * @param fileName
     * @return
     */
    @Override
    public String uploadPic(byte[] bytes, String fileName) {

        // 1.检查文件大小是否超过限制
        long fileSizeInBytes = bytes.length;
        long maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB
        if (fileSizeInBytes > maxFileSizeInBytes) {
            throw new CustomException(AppHttpCodeEnum.FILE_DATE_MAX);
        }

        Configuration cfg = new Configuration(Region.huanan()); // 区域
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2; // 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        String substring = fileName.substring(0, 3);
        // 获取当前时间并格式化
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        // 获取文件后缀
        String fileExtension = getFileExtension(fileName);
        String name = substring + "-" + timestamp + fileExtension;

        String key = "umpires/" + name; // 文件名
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());

        try {
            Response response = uploadManager.put(bytes, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            if (putRet != null && !putRet.key.isEmpty()) {
                String imageUrl = qiniuConfig.getHostName() + putRet.key;
                return imageUrl;
            } else {
                System.err.println("上传失败，未返回有效的图片地址！");
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println("上传失败，错误信息：" + r.toString());
            try {
                System.err.println("上传失败，详细错误信息：" + r.bodyString());
            } catch (QiniuException ex2) {
                System.err.println("获取详细错误信息时发生异常：" + ex2.getMessage());
            }
        }
        return null;
    }

    // 获取文件后缀
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    /**
     * 2.图片批量删除
     *
     * @param picNames
     * @return
     */
    @Override
    public Boolean deleteList(DeletePics picNames) {
        String[] pics = picNames.getPics();
        if (pics.length > 1000) {
            throw new CustomException(AppHttpCodeEnum.FILE_NUMBER_MAX);
        }
        Configuration cfg = new Configuration(Region.huanan());
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(qiniuConfig.getBucket(), pics);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < pics.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = pics[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                    return true;
                } else {
                    System.out.println(status.data.error);
                    return false;
                }
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return null;
    }

    /**
     * 3.删除文件
     */
    @Override
    public Boolean deletePic(DeletePic fileName) {
        Configuration cfg = new Configuration(Region.huanan());
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(qiniuConfig.getBucket(), fileName.getPic());
            return true; // 删除成功
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false; // 删除失败
        }
    }

}

package com.house.xxl.common;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

public class Ali {

    public static String uploadFile(MultipartFile file) throws IOException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI4FzgoMLSbkENv4XmerBS";
        String accessKeySecret = "RMbMylEkes4AjjWxi808SLc4unjTwe";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "zzhimage";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流
        try {
            // 获取文件的名称
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            // 调用oss的方法实现长传
            // 第一个参数 bucketName
            // 第二个参数 上传到oss的文件路径和文件名称
            ossClient.putObject(bucketName, fileName, file.getInputStream(),objectMetadata);
            // 关闭OSSClient。
            ossClient.shutdown();
            // 把上传的文件路径返回 （手动拼接）
            // 这里设置图片有效时间 我设置了30年
            Date expiration = new Date(System.currentTimeMillis() + 946080000 * 1000);
            String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 实现图片的预览功能
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }
}

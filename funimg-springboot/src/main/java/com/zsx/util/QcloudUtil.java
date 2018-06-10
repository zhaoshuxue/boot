package com.zsx.util;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by highness on 2018/5/27 0027.
 */
public class QcloudUtil {


    public static String upload(String accessKey, String secretKey, String regionName, String bucketName, String domain, String key, File file) {
        COSClient cosClient = null;
        try {
            //      1 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
            //      2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region(regionName));
            //      3 生成cos客户端
            cosClient = new COSClient(cred, clientConfig);
            //      bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
            //        String bucketName = "";

            // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
            //      大文件上传请参照 API 文档高级 API 上传
            //        File localFile = new File("D:\\\\git\\\\static\\\\images\\\\20180527\\\\bbc16391-3c98-4afb-bd65-46d5f3814a70.jpg");
            //      指定要上传到 COS 上的路径
            //        String key = "/zhao/bbc16391-3c98-4afb-bd65-46d5f3814a70.jpg";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            //        putObjectResult.
            //        System.out.println(JSON.toJSONString(putObjectResult));
            String eTag = putObjectResult.getETag();
            if (StringUtils.isNotBlank(eTag)) {
                return domain + key;
//                return "https://highness-1-1253922088.cos.ap-beijing.myqcloud.com" + key;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端(关闭后台线程)
            if (cosClient != null) {
                cosClient.shutdown();
                cosClient = null;
            }
        }
        return "";
    }

}

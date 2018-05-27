import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.IOException;

/**
 * Created by highness on 2018/5/27 0027.
 */
public class UnitTest1 {

    public static void main(String[] args) throws IOException {
        String originalFilename = "阿斯蒂芬骄傲了书法家拉上就分散，阿斯顿发顺丰的啊，，sdf.jpg";
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(fileNameSuffix);
        System.out.println(originalFilename.substring(0, originalFilename.lastIndexOf(".")));

    }
}

package com.zsx.controller;

import com.zsx.util.QcloudUtil;
import com.zsx.vo.json.JsonData;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by highness on 2018/5/25 0025.
 */
@RequestMapping("upload")
@Controller
public class UploadController {

    /**
     * 配置文件中的配置
     */
    @Value("${tempFile.win}")
    private String tempFileWinPath;
    @Value("${tempFile.linux}")
    private String tempFileLinuxPath;


    @Value("${qcloud.accessKey}")
    private String accessKey;
    @Value("${qcloud.secretKey}")
    private String secretKey;
    @Value("${qcloud.region_name}")
    private String regionName;
    @Value("${qcloud.bucket}")
    private String bucket;
    //    @Value("${qcloud.prefix}")
    private String keyPrefix = "/wang/";
    @Value("${qcloud.domain}")
    private String CDNdomain;


    /**
     * 上传
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public JsonData upload(
            @RequestParam(value = "tupian", required = true) MultipartFile file,
            HttpServletRequest request) {
        try {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//                String fileTitle = originalFilename.substring(0, originalFilename.lastIndexOf("."));
//                统一为小写
                fileNameSuffix = fileNameSuffix.toLowerCase();
                String uuid = UUID.randomUUID().toString();
                String tempFileName = uuid + fileNameSuffix;

//              获取视频的临时存储路径
                String videoPath = getVideoPath(request);
//              暂存
                file.transferTo(new File(videoPath + tempFileName));
//              临时文件
                File tempFile = new File(videoPath + tempFileName);

                //                获取图片的宽高
                BufferedImage bufferedImage = ImageIO.read(tempFile);
                int width = bufferedImage.getWidth(null);
                int height = bufferedImage.getHeight(null);
//                处理后的宽高
                int[] widthHeight = getWidthHeight(width, height);
                width = widthHeight[0];
                height = widthHeight[1];

                InputStream tempFileIs = new FileInputStream(tempFile);
                Thumbnails
                        .of(tempFileIs)
                        .sourceRegion(Positions.CENTER, width, height)
                        .size(400, 300)
                        .keepAspectRatio(false) // 不锁定宽高比
//                        .scale(0.5f) // 图片缩小一半
                        .outputQuality(0.7f) // 设置图片质量
                        .toFile(new File(videoPath + "wang-" + tempFileName));

                File tempFile2 = new File(videoPath + "wang-" + tempFileName);

//                上传
                String uploadMp4Url = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, CDNdomain, keyPrefix + tempFileName, tempFile2);
                if (StringUtils.isBlank(uploadMp4Url)) {
                    return JsonData.fail("上传失败");
                }
                return JsonData.returnObject(uploadMp4Url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return JsonData.fail("上传失败");
    }

    private String getVideoPath(HttpServletRequest request) {
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
        String path = isWindow() ? tempFileWinPath : tempFileLinuxPath;
        path = path + "static" + File.separator + "wangjb" + File.separator + yyyyMMdd + File.separator;
        File rootFile = new File(path);
//            路径不存在，则创建
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return path;
    }

    private boolean isWindow() {
        return System.getProperty("os.name").toUpperCase().startsWith("WIN");
    }

    private int[] getWidthHeight(int width, int height) {
        int[] result = new int[2];
        result[0] = width;
        result[1] = height;
        BigDecimal ratio = new BigDecimal("1.33");
        BigDecimal divide = new BigDecimal(width).divide(new BigDecimal(height), 2, BigDecimal.ROUND_HALF_UP);
        if (divide.compareTo(ratio) == 1) {
//          太宽了，取高，然后算出对应比例的宽度
            int newWidth = new BigDecimal(height).multiply(ratio).intValue();
            result[0] = newWidth;
        } else if (divide.compareTo(ratio) == -1) {
//          太高了，取宽，然后算出对应比例的高度
            int newHeight = new BigDecimal(width).divide(ratio, 2, BigDecimal.ROUND_HALF_UP).intValue();
            result[1] = newHeight;
        }
        return result;
    }
}

package com.zsx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zsx.entity.FunImages;
import com.zsx.entity.FunPanoramaImage;
import com.zsx.service.FunAlbumService;
import com.zsx.service.ImageService;
import com.zsx.service.PanoramaService;
import com.zsx.util.PageData;
import com.zsx.util.QcloudUtil;
import com.zsx.vo.FunPanorama;
import com.zsx.vo.app.AlbumData;
import com.zsx.vo.json.JsonData;
import com.zsx.vo.json.JsonTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("pano")
@Controller
@CrossOrigin
public class PanoramaController {

    /**
     * 配置文件中的配置
     */
    @Value("${ffmpeg.win}")
    private String ffmpegWinExe;
    @Value("${ffmpeg.linux}")
    private String ffmpegLinuxExe;
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
    @Value("${qcloud.prefix}")
    private String keyPrefix;
    @Value("${qcloud.domain}")
    private String CDNdomain;


    @Autowired
    private PanoramaService panoramaService;

    @PostMapping("/lists")
    @ResponseBody
    public JsonTable getList(
            @RequestParam(defaultValue = "") String del,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        JsonTable jsonTable = null;
        HashMap<String, Object> search = Maps.newHashMap();
        if (StringUtils.isNotBlank(del)) {
            search.put("del", Integer.parseInt(del));
        }
        PageData<FunPanoramaImage> pageData = panoramaService.getFunPanoramaImagePageList(search, pageNum, pageSize);
        if (pageData.getList() != null) {
            List<FunPanoramaImage> list = pageData.getList();

            List<FunPanorama> resultList = JSONArray.parseArray(JSON.toJSONString(list), FunPanorama.class);

            jsonTable = JsonTable.toTable(pageData.getTotal(), resultList);
        }
        return jsonTable;
    }

    /**
     * 上传
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public JsonData addImage(
            @RequestParam(value = "tupian", required = true) MultipartFile file,
            HttpServletRequest request) {
        InputStream is = null;
        try {
            String title = request.getParameter("title");
            System.out.println(title);
            String isDel = request.getParameter("isDel");
            Integer del = 0;
            if (StringUtils.isNotBlank(isDel)) {
                del = Integer.parseInt(isDel);
            }

            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileTitle = originalFilename.substring(0, originalFilename.lastIndexOf("."));

                if (!fileNameSuffix.equals(".jpg")) {
                    return JsonData.fail("图片格式必须为jpg");
                }

//                统一为小写
                fileNameSuffix = fileNameSuffix.toLowerCase();
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                String tempFileName = uuid + fileNameSuffix;

//              获取图片的临时存储路径
                String picturePath = getPicturePath(request);
//              暂存
                file.transferTo(new File(picturePath + tempFileName));
//              临时文件
                File tempFile = new File(picturePath + tempFileName);

                int width = 0, height = 0, imgType = 1;

//               图片地址
                String imgUrl = "";
//                上传
                String uploadImgUrl = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, CDNdomain, keyPrefix + tempFileName, tempFile);
                if (StringUtils.isBlank(uploadImgUrl)) {
                    return JsonData.fail("上传失败");
                }
                imgUrl = uploadImgUrl;
//                缩略图地址
                String thumbnail = "";

                // 获取图片的宽高
                BufferedImage bufferedImage = ImageIO.read(tempFile);
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();


//                if (fileNameSuffix.equals(".mp4")) {
//                        String thumbnailFileUrl = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, CDNdomain, keyPrefix + uuid + ".jpg", thumbnailFile);
//
//                    if (StringUtils.isNotBlank(thumbnailFileUrl)) {
//                        thumbnail = thumbnailFileUrl;
//                    }
//                } else {
//
//                }

//                保存
                FunPanoramaImage panoramaImage = new FunPanoramaImage();
                panoramaImage.setImgUuid(uuid);
                panoramaImage.setThumbnail(thumbnail);
                if (StringUtils.isBlank(title)) {
                    title = fileTitle;
                }
                panoramaImage.setTitle(title);
                panoramaImage.setImgUrl(imgUrl);
                panoramaImage.setImgType(imgType);
                panoramaImage.setWidth(width);
                panoramaImage.setHeight(height);
                panoramaImage.setFileSize(file.getSize());

                panoramaImage.setDel(del);
                panoramaImage.setCreateTime(new Date());
                panoramaImage.setUpdateTime(new Date());
                panoramaImage.setCreatorId("");
                panoramaImage.setCreatorName("");
                panoramaImage.setUpdaterId("");
                panoramaImage.setUpdaterName("");
//
                System.out.println(JSON.toJSONString(panoramaImage));
//                todo
                JsonData jsonData = panoramaService.addFunPanoramaImage(panoramaImage);
                if (is != null) {
                    is.close();
                }
                return JsonData.returnObject(jsonData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return JsonData.fail("上传失败");
    }

    private String getPicturePath(HttpServletRequest request) {
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String path = isWindow() ? tempFileWinPath : tempFileLinuxPath;
        path = path + "static" + File.separator + "images" + File.separator + yyyyMMdd + File.separator;
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

    @PostMapping("addImage")
    @ResponseBody
    public JsonData addImage(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "sinaimgUrl") String sinaimgUrl,
            @RequestParam(value = "qiniuImgUrl") String qiniuImgUrl
    ) {
        FunImages funImages = new FunImages();
        funImages.setImgUuid("");
        funImages.setThumbnail("");
        funImages.setTitle(title);
        funImages.setImgUrl("");
        String url = "";
        if (StringUtils.isNotBlank(sinaimgUrl)) {
            url = sinaimgUrl;
        } else if (StringUtils.isNotBlank(qiniuImgUrl)) {
            url = qiniuImgUrl;
        }
        String fileNameSuffix = url.substring(url.lastIndexOf("."));
//                统一为小写
        fileNameSuffix = fileNameSuffix.toLowerCase();

        Integer imgType = null;
        if (fileNameSuffix.equals(".gif")) {
            imgType = 0;
        } else if (fileNameSuffix.equals(".jpg")) {
            imgType = 1;
        } else if (fileNameSuffix.equals(".jpeg")) {
            imgType = 2;
        } else if (fileNameSuffix.equals(".png")) {
            imgType = 3;
        }

        funImages.setSinaimgUrl(sinaimgUrl);
        funImages.setQiniuImgUrl(qiniuImgUrl);
        funImages.setImgType(imgType);
        funImages.setWidth(0);
        funImages.setHeight(0);
        funImages.setFileSize(0L);

        funImages.setDel(2);
        funImages.setCreateTime(new Date());
        funImages.setUpdateTime(new Date());
        funImages.setCreatorId("");
        funImages.setCreatorName("");
        funImages.setUpdaterId("");
        funImages.setUpdaterName("");

        // todo
        JsonData jsonData = panoramaService.addFunPanoramaImage(null);
        return jsonData;
    }


}

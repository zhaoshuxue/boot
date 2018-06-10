package com.zsx.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zsx.entity.FunImages;
import com.zsx.entity.FunVideo;
import com.zsx.service.ImageService;
import com.zsx.service.VideoService;
import com.zsx.util.PageData;
import com.zsx.util.QcloudUtil;
import com.zsx.vo.json.JsonData;
import com.zsx.vo.json.JsonTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by highness on 2018/5/25 0025.
 */
@RequestMapping("video")
@Controller
public class VideoController {

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


    @Autowired
    private VideoService videoService;

    @PostMapping("/lists")
    @ResponseBody
    public JsonTable getList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        PageData<FunVideo> pageData = videoService.getFunVideoPageList(search, pageNum, pageSize);
        JsonTable jsonTable = JsonTable.toTable(pageData.getTotal(), pageData.getList());
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
            @RequestParam(value = "video", required = true) MultipartFile file,
            HttpServletRequest request) {
        InputStream is = null;
        try {
            String title = request.getParameter("title");
//            System.out.println(title);
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileTitle = originalFilename.substring(0, originalFilename.lastIndexOf("."));
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

                int width = 0, height = 0, imgType = 0;
//                视频第一帧图片
                String thumbnail = "";
//               图片地址
                String imgUrl = "";
//                上传
                String uploadImgUrl = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, keyPrefix + tempFileName, tempFile);
                if (StringUtils.isBlank(uploadImgUrl)) {
                    return JsonData.fail("上传失败");
                }
                imgUrl = uploadImgUrl;

                if (fileNameSuffix.equals(".mp4")) {
                    imgType = 4;
//                    得到第一帧图片
                    String tempFilePath = picturePath + tempFileName;
                    String thumbnailFilePath = picturePath + uuid + ".jpg";


                    String exeFile = isWindow() ? "D:/ProgramFiles/ffmpeg/bin/ffmpeg.exe" : "/usr/local/bin/ffmpeg";

                    String[] cmd = {exeFile, "-i", tempFilePath, "-r", "1", "-vframes", "1", "-f", "image2", thumbnailFilePath};

                    Process p = Runtime.getRuntime().exec(cmd);

                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
                    String s = "";
                    while ((s = br.readLine()) != null) {
                        System.out.println("返回数据： " + s);
                    }
                    br.close();

                    File thumbnailFile = new File(thumbnailFilePath);
                    if (thumbnailFile.exists()) {
                        // 获取图片的宽高
                        BufferedImage bufferedImage = ImageIO.read(thumbnailFile);
                        width = bufferedImage.getWidth();
                        height = bufferedImage.getHeight();

                        String thumbnailFileUrl = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, keyPrefix + uuid + ".jpg", thumbnailFile);
                        if (StringUtils.isNotBlank(thumbnailFileUrl)) {
                            thumbnail = thumbnailFileUrl;
                        }
                    }
                } else {
                    if (fileNameSuffix.equals(".jpg")) {
                        imgType = 1;
                    } else if (fileNameSuffix.equals(".jpeg")) {
                        imgType = 2;
                    } else if (fileNameSuffix.equals(".png")) {
                        imgType = 3;
                    }
                    // 获取图片的宽高
                    BufferedImage bufferedImage = ImageIO.read(tempFile);
                    width = bufferedImage.getWidth();
                    height = bufferedImage.getHeight();
                }

//                保存
                FunImages funImages = new FunImages();
                funImages.setImgUuid(uuid);
                funImages.setThumbnail(thumbnail);
                if (StringUtils.isBlank(title)){
                    title = fileTitle;
                }
                funImages.setTitle(title);
                funImages.setImgUrl(imgUrl);
                funImages.setImgType(imgType);
                funImages.setWidth(width);
                funImages.setHeight(height);
                funImages.setFileSize(file.getSize());

                funImages.setDel(0);
                funImages.setCreateTime(new Date());
                funImages.setUpdateTime(new Date());
                funImages.setCreatorId("");
                funImages.setCreatorName("");
                funImages.setUpdaterId("");
                funImages.setUpdaterName("");
//
                System.out.println(JSON.toJSONString(funImages));
                JsonData jsonData = imageService.addFunImages(funImages);
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

    private String getVideoPath(HttpServletRequest request) {
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
        String path = isWindow() ? tempFileWinPath : tempFileLinuxPath;
        path = path + "static" + File.separator + "video" + File.separator + yyyyMMdd + File.separator;
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

    @PostMapping("add")
    @ResponseBody
    public JsonData addAlbum(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "imgUrl") String imgUrl,
            @RequestParam(value = "publish_date") String publish_date
    ) {
//        return funAlbumService.addAlbum(funAlbum);
        return null;
    }


}

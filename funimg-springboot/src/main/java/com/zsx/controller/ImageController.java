package com.zsx.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zsx.entity.FunImages;
import com.zsx.service.FunAlbumService;
import com.zsx.service.ImageService;
import com.zsx.util.PageData;
import com.zsx.util.QcloudUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by highness on 2018/5/25 0025.
 */
@RequestMapping("image")
@Controller
@CrossOrigin
public class ImageController {

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
    private ImageService imageService;
    @Autowired
    private FunAlbumService funAlbumService;

    @PostMapping("/lists")
    @ResponseBody
    public JsonTable getList(
//            @ApiParam(value = "区域id") @RequestParam(value = "districtId", defaultValue = "") String districtId,
//            @ApiParam(value = "模糊查询文本") @RequestParam(value = "searchText", required = false) String searchText,
//            @ApiParam("租金范围") @RequestParam(value = "rentalRange", required = false) String rentalRange,
//            @ApiParam("其它条件，1：最近发布，2：距离由近到远，3：租金由低到高，4：租金由高到低") @RequestParam(value = "filter", required = false) String filter,
//            @ApiParam("当选择距离由近到远时，该值为纬度") @RequestParam(value = "lng", required = false) String lng,
//            @ApiParam("当选择距离由近到远时，该值为经度") @RequestParam(value = "lat", required = false) String lat,
//            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
//            @ApiParam("条数") @RequestParam(defaultValue = "10") Integer pageSize

            @RequestParam(defaultValue = "") String del,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        if (StringUtils.isNotBlank(del)){
            search.put("del", Integer.parseInt(del));
        }
        PageData<FunImages> pageData = imageService.getFunImagesPageList(search, pageNum, pageSize);
        JsonTable jsonTable = JsonTable.toTable(pageData.getTotal(), pageData.getList());
        return jsonTable;
    }


    @PostMapping("/hotImages")
    @ResponseBody
    public PageData getHotImageList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        PageData<AlbumData> pageData = funAlbumService.getFunHotImagePageList(search, pageNum, pageSize);
        return pageData;
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
//            System.out.println(file.isEmpty()); // false
//            System.out.println(file.getContentType()); // image/jpeg
//            System.out.println(file.getName()); // stream
//            System.out.println(file.getOriginalFilename()); // 微信图片_20170830144659.jpg
//            System.out.println(file.getSize()); // 44982  字节
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileTitle = originalFilename.substring(0, originalFilename.lastIndexOf("."));
//                统一为小写
                fileNameSuffix = fileNameSuffix.toLowerCase();
                String uuid = UUID.randomUUID().toString();
                String tempFileName = uuid + fileNameSuffix;
                String format = new SimpleDateFormat("yyMMdd").format(new Date());
//                String fileName = format + "_" + uuid + fileNameSuffix;

//              获取图片的临时存储路径
                String picturePath = getPicturePath(request);
//              暂存
                file.transferTo(new File(picturePath + tempFileName));
//              临时文件
                File tempFile = new File(picturePath + tempFileName);

                int width = 0, height = 0, imgType = 0;
//                视频第一帧图片
                String thumbnail = "";
//               图片地址
                String imgUrl = "";
//                上传
                String uploadImgUrl = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, CDNdomain, keyPrefix + tempFileName, tempFile);
                if (StringUtils.isBlank(uploadImgUrl)) {
                    return JsonData.fail("上传失败");
                }
                imgUrl = uploadImgUrl;

                if (fileNameSuffix.equals(".mp4")) {
                    imgType = 4;
//                    得到第一帧图片
                    String tempFilePath = picturePath + tempFileName;
                    String thumbnailFilePath = picturePath + uuid + ".jpg";


                    String exeFile = isWindow() ? ffmpegWinExe : ffmpegLinuxExe;

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

                        String thumbnailFileUrl = QcloudUtil.upload(accessKey, secretKey, regionName, bucket, CDNdomain, keyPrefix + uuid + ".jpg", thumbnailFile);
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
                if (StringUtils.isBlank(title)) {
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

        JsonData jsonData = imageService.addFunImages(funImages);
        return jsonData;
    }


}

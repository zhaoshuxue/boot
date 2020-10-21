package com.zsx.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zsx.entity.FunPanoramaImage;
import com.zsx.service.FunAlbumService;
import com.zsx.service.PanoramaService;
import com.zsx.service.VideoService;
import com.zsx.util.HttpUtil;
import com.zsx.util.PageData;
import com.zsx.vo.app.*;
import com.zsx.vo.json.JsonData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by highness on 2018/5/10 0010.
 */
//@Api(description = "小程序房屋接口")
@RequestMapping(path = "/api/funimg")
@RestController
public class FunImgController {

    /**
     * 配置文件中的配置
     */
    @Value("${miniapp.appId}")
    private String appId;
    @Value("${miniapp.appsecret}")
    private String appsecret;

    @Autowired
    private FunAlbumService funAlbumService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private PanoramaService panoramaService;

    //    @ApiOperation(value = "获取房屋列表", notes = "供小程序调用", httpMethod = "GET")
    @GetMapping("/lists")
    public PageData getUserList(
//            @ApiParam(value = "区域id") @RequestParam(value = "districtId", defaultValue = "") String districtId,
//            @ApiParam(value = "模糊查询文本") @RequestParam(value = "searchText", required = false) String searchText,
//            @ApiParam("租金范围") @RequestParam(value = "rentalRange", required = false) String rentalRange,
//            @ApiParam("其它条件，1：最近发布，2：距离由近到远，3：租金由低到高，4：租金由高到低") @RequestParam(value = "filter", required = false) String filter,
//            @ApiParam("当选择距离由近到远时，该值为纬度") @RequestParam(value = "lng", required = false) String lng,
//            @ApiParam("当选择距离由近到远时，该值为经度") @RequestParam(value = "lat", required = false) String lat,
//            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
//            @ApiParam("条数") @RequestParam(defaultValue = "10") Integer pageSize

            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        search.put("status", 0);
        PageData<AlbumList> pageData = funAlbumService.getFunAlbumPageList(search, pageNum, pageSize);
        return pageData;
    }


    @GetMapping("/albumData")
    public AlbumDetail getAlbumData(
            @RequestParam(defaultValue = "0") Long albumId
    ) {
        AlbumDetail albumDetail = funAlbumService.getAlbumData(albumId);
        return albumDetail;
    }


    @GetMapping("/hotImgs")
    public PageData getHotImageList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        PageData<AlbumData> pageData = funAlbumService.getFunHotImagePageList(search, pageNum, pageSize);
        return pageData;
    }

    @GetMapping("/imgComment")
    public ImageComment getCommentData(
            @RequestParam(defaultValue = "0") Long id
    ) {
        ImageComment comment = funAlbumService.getImageComment(id);
        return comment;
    }


    //    @ApiOperation(value = "获取小程序用户openid", notes = "", httpMethod = "GET")
    @GetMapping("/getOpenId")
    public JsonData getOpenId(
//            @ApiParam(value = "code")
            @RequestParam(value = "code") String code
    ) {
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" +
                    appId + "&secret=" +
                    appsecret + "&js_code=" +
                    code + "&grant_type=authorization_code";
            String str = HttpUtil.httpGet(url);
            JSONObject object = JSONObject.parseObject(str);
            String openid = object.getString("openid");
            return JsonData.returnObject(openid);
        } catch (Exception e) {
            return JsonData.fail("");
        }
    }

    @PostMapping("addComment")
    public JsonData addFavorite(
//            @ApiParam(value = "用户openid")
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "toOpenid", defaultValue = "") String toOpenid,
            @RequestParam(value = "nickName", defaultValue = "") String nickName,
            @RequestParam(value = "head", defaultValue = "") String head,
            @RequestParam(value = "text", defaultValue = "") String text,
//            @ApiParam(value = "房屋id")
            @RequestParam(value = "id", defaultValue = "0") Long id
    ) {
        if (StringUtils.isBlank(openid)) {
            return JsonData.fail("");
        }
        if (StringUtils.isBlank(text)) {
            return JsonData.fail("");
        }
        Comment comment = new Comment();

        comment.setFromUser(openid);
        comment.setToUser(toOpenid);
        comment.setNickName(nickName);
        comment.setHeadImg(head);
        comment.setAlbumDetailId(id);
        comment.setText(text);

        return funAlbumService.addComment(comment);
    }


    @GetMapping("/videos")
    public PageData getVideoList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        PageData<VideoData> pageData = videoService.getFunVideoDataPageList(search, pageNum, pageSize);
        return pageData;
    }

    @GetMapping("/videoDetail")
    public VideoData getVideoDetail(@RequestParam(defaultValue = "0") Long id) {
        return videoService.getVideoData(id);
    }


    @GetMapping("/panoList")
    public PageData getPanoList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageData<FunPanoramaImage> pageData = panoramaService.getFunPanoramaImagePageList(new HashMap<String, Object>(1), pageNum, pageSize);

        List<FunPanoramaImage> list = pageData.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<FunPanoramaImage> images = Lists.newArrayList();

            for (FunPanoramaImage panoramaImage : list) {
                FunPanoramaImage image = new FunPanoramaImage();

                image.setId(panoramaImage.getId());
                image.setTitle(panoramaImage.getTitle());
                image.setThumbnail(panoramaImage.getThumbnail());

                images.add(image);
            }

            pageData.setList(images);
        }
        return pageData;
    }

    @GetMapping("/panoDetail")
    public JsonData getPanoDetail(@RequestParam(defaultValue = "0") Long id) {
        return panoramaService.getPanoramaImage(id);
    }
}

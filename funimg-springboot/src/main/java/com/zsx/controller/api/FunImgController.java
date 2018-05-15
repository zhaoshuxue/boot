package com.zsx.controller.api;

import com.google.common.collect.Maps;
import com.zsx.service.FunAlbumService;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumData;
import com.zsx.vo.app.AlbumDetail;
import com.zsx.vo.app.AlbumList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by highness on 2018/5/10 0010.
 */
//@Api(description = "小程序房屋接口")
@RequestMapping(path = "/api/funimg")
@RestController
public class FunImgController {

    @Autowired
    private FunAlbumService funAlbumService;

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

}

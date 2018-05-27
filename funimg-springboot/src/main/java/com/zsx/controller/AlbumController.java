package com.zsx.controller;

import com.google.common.collect.Maps;
import com.zsx.entity.FunAlbum;
import com.zsx.service.FunAlbumService;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumDetail;
import com.zsx.vo.app.AlbumList;
import com.zsx.vo.json.JsonData;
import com.zsx.vo.json.JsonTable;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by highness on 2018/5/25 0025.
 */
@RequestMapping(path = "/album")
@Controller
public class AlbumController {

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
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        HashMap<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        PageData<FunAlbum> pageData = funAlbumService.queryFunAlbumPageList(search, pageNum, pageSize);
        JsonTable jsonTable = JsonTable.toTable(pageData.getTotal(), pageData.getList());
        return jsonTable;
    }


    @PostMapping("add")
    @ResponseBody
    public JsonData addAlbum(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "imgUrl") String imgUrl,
            @RequestParam(value = "publish_date") String publish_date
    ) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime publishDate = DateTime.parse(publish_date, formatter);

        FunAlbum funAlbum = new FunAlbum();
        funAlbum.setPublishDate(publishDate.toDate());
        funAlbum.setTitle(title);
        funAlbum.setImgUuid("");
        funAlbum.setImgUrl(imgUrl);
        funAlbum.setStatus(1);
        funAlbum.setDel(0);
        funAlbum.setCreateTime(new Date());
        funAlbum.setUpdateTime(new Date());
        funAlbum.setCreatorId("");
        funAlbum.setCreatorName("");
        funAlbum.setUpdaterId("");
        funAlbum.setUpdaterName("");
        return funAlbumService.addAlbum(funAlbum);
    }



    @PostMapping("albumDetailList")
    @ResponseBody
    public JsonTable getAlbumDetailList(
            @RequestParam(value = "albumId") Long albumId
    ) {
        AlbumDetail albumData = funAlbumService.getAlbumData(albumId);
        JsonTable jsonTable = JsonTable.toTable(50L, albumData.getAlbumData());
        return jsonTable;
    }
}

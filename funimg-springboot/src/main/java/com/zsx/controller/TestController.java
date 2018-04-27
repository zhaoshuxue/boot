package com.zsx.controller;

import com.zsx.dao.RentImagesDao;
import com.zsx.dao.RentImagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by highness on 2018/4/28 0028.
 */
@RestController
public class TestController {

    @Autowired
    private RentImagesDao rentImagesDao;
    @Autowired
    private RentImagesMapper rentImagesMapper;

    @RequestMapping("img")
    @ResponseBody
    public Object toIndex(Long id){
        return rentImagesDao.selectByPrimaryKey(id);
    }
}

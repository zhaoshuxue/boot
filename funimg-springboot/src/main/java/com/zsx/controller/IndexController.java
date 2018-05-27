package com.zsx.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZSX on 2018/1/20.
 *
 * @author ZSX
 */
@Controller
public class IndexController {

    private void getBasePath(HttpServletRequest request, Model model){
        String getContextPath = request.getContextPath();
        String scheme = request.getHeader("X-Forwarded-Scheme");
        if (StringUtils.isBlank(scheme)){
            scheme = request.getScheme();
        }
        String basePath = scheme + "://" + request.getServerName() + ":" + request.getServerPort() + getContextPath + "/";
        model.addAttribute("basePath", basePath);
    }

    @RequestMapping("index")
    public String toIndex(Model model) {

        model.addAttribute("name", "xue");
        return "albumPage/albumList";
    }

    @RequestMapping("albumDetail")
    public String gotoAlbumDetail(HttpServletRequest request, Model model) {
        model.addAttribute("albumId", request.getParameter("id"));
        return "albumPage/albumDetail";
    }

    @RequestMapping("imageList")
    public String gotoImageList(HttpServletRequest request, Model model) {
        this.getBasePath(request, model);
        return "imagePage/imageList";
    }

}

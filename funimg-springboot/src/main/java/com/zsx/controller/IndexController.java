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
//        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(scheme)){
            scheme = request.getScheme();
        }
        String port = request.getServerPort() == 80 || request.getServerPort() == 443 ? ""
                : ":" + request.getServerPort();
        String basePath = scheme + "://" + request.getServerName() + port + getContextPath;
        model.addAttribute("basePath", basePath);
    }

    @RequestMapping("index")
    public String toIndex(HttpServletRequest request, Model model) {
        this.getBasePath(request, model);
        return "albumPage/albumList";
    }

    @RequestMapping("albumDetail")
    public String gotoAlbumDetail(HttpServletRequest request, Model model) {
        this.getBasePath(request, model);
        model.addAttribute("albumId", request.getParameter("id"));
        return "albumPage/albumDetail";
    }

    @RequestMapping("imageList")
    public String gotoImageList(HttpServletRequest request, Model model) {
        this.getBasePath(request, model);
        return "imagePage/imageList";
    }

}

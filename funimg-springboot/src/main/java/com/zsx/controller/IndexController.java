package com.zsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZSX on 2018/1/20.
 *
 * @author ZSX
 */
@Controller
public class IndexController {

    @RequestMapping("index")
    public String toIndex(Model model){

        model.addAttribute("name", "xue");
        return "albumPage/albumList";
    }

}

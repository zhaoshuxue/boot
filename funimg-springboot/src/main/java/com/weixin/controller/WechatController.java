package com.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.weixin.service.impl.WechatService;
import com.weixin.util.SignUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatService wechatService;


    @RequestMapping(value = "/validate/{name}", method = RequestMethod.GET)
    public void wechatGet(HttpServletRequest request,
                          HttpServletResponse response,
                          @PathVariable("name") String name) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳  
        String timestamp = request.getParameter("timestamp");
        // 随机数  
        String nonce = request.getParameter("nonce");
        // 随机字符串  
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        //根据参数获取该注册用户的信息——token ， 用于验证用户
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String token = "zhaoshuxue";
        if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
            out.print(echostr);
        }
//		System.out.println("验证 :   " + name);
        out.close();
        out = null;
    }

    @RequestMapping(value = "/validate/{name}", method = RequestMethod.POST)
    public void wechatPost(HttpServletResponse response,
                           HttpServletRequest request,
                           @PathVariable("name") String name) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        System.out.println("消息 :   " + name);

        String respMessage = wechatService.coreService(request);

        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }

}

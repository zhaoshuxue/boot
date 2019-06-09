package com.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.util.MessageUtil;
import com.weixin.util.TuLing;
import org.springframework.stereotype.Service;
import com.weixin.entity.message.resp.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WechatService {

    public String coreService(HttpServletRequest request) {

        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //
            String msgId = requestMap.get("MsgId");

            String createTime = requestMap.get("CreateTime");

            // 默认回复此文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 将文本消息对象转换成xml字符串
            respMessage = MessageUtil.textMessageToXml(textMessage);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content");

                /**
                 * 这里判断消息内容，并确定返回内容
                 */
                if ("笑话".equals(content)) {
                    respContent = "http://funimg.duapp.com/sys/xiaohua";
                } else if ("测试".equals(content)) {
                    respContent = "http://funimg.duapp.com/sys/ceshi";
                } else if ("帮助".equals(content)) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("1、回复“笑话”，可以得到搞笑图片网址").append("\n");
                    sb.append("2、回复“qq+qq号码”，可以查看qq昵称和空间头像").append("\n");
                    sb.append("3、回复“今天历史”，可以查看历史上的今天大事纪").append("\n");
                    sb.append("4、回复“拼音+文字”，可以将文字转换为拼音").append("\n");

                    respContent = sb.toString();
                } else if (content.startsWith("qq", 0)) {
//					respContent = BaiduApis.api_qq(content.substring(2));
                } else if (content.startsWith("拼音", 0)) {
//					respContent = BaiduApis.api_hanzizhuanpinyin(content.substring(2));
                } else if ("今天历史".equals(content)) {
//					respContent = BaiduApis.api_todayInHistory();
                } else {
					String tulingRes = TuLing.reboot(content);
					JSONObject jsonObject = JSON.parseObject(tulingRes);
					if (jsonObject.getIntValue("code") == 100000) {
						respContent = jsonObject.getString("text");
					}else {
						respContent = content;
					}
                }
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                String picUrl = requestMap.get("PicUrl");
                String mediaId = requestMap.get("MediaId");
                //

                textMessage.setContent(picUrl);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;

            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
                String MediaId = requestMap.get("MediaId");
                String Format = requestMap.get("Format");
                //


                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                String MediaId = requestMap.get("MediaId");
                String ThumbMediaId = requestMap.get("ThumbMediaId");
                //


                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;
            }
            // 小视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
                String MediaId = requestMap.get("MediaId");
                String ThumbMediaId = requestMap.get("ThumbMediaId");
                //


                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";

                String Location_X = requestMap.get("Location_X");
                String Location_Y = requestMap.get("Location_Y");
                String Scale = requestMap.get("Scale");
                String Label = requestMap.get("Label");
                //


                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                String Title = requestMap.get("Title");
                String Description = requestMap.get("Description");
                String Url = requestMap.get("Url");
                //


                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
                return respMessage;
            }

            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                //事件KEY值
//				String eventKey = requestMap.get("EventKey");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    return respMessage;
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }

                // 扫描带参数二维码事件 —— 用户已关注时的事件推送
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    respContent = "扫描二维码——您已关注！";

                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    return respMessage;

                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    //事件KEY值，与自定义菜单接口中KEY值对应
//					String key = requestMap.get("EventKey");
                }

                // 自定义菜单点击跳转链接事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
                    //事件KEY值，设置的跳转URL
//					String key = requestMap.get("EventKey");
                }
            }

//			textMessage.setContent(respContent);
//			respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }


}

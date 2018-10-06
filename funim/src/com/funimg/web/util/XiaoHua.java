package com.funimg.web.util;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class XiaoHua {
	
	/**
	 * 获取笑话网站
	 * @return  第一个为标题   第二个为图片地址
	 */
	public static String[] get() {
		String[] res = new String[2];
		// 从 1 到 900 
		Random random = new Random();
		String num = random.nextInt(900) + "";
		try {
			Document document = Jsoup.connect("http://www.xxhh.com/mnxf/v4/" + num + ".html").get();
			Element title = document.getElementById("title");
			Element dingImg = document.getElementById("ding-img");
//			System.out.println(title.html());
			res[0] = title.html();
			Element child = dingImg.child(0);
//			System.out.println(child.attr("src"));
			res[1] = child.attr("src");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}

}

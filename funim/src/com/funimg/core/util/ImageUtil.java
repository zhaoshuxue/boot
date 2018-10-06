package com.funimg.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片相关工具类
 * @author zsx
 *
 */
public class ImageUtil {

	/**
	 * 将网络图片传到百度存储中
	 * @param url
	 * @param floder
	 * @param imgName
	 * @param acl
	 * @return
	 */
	/*public static String uploadNetImage(String url, String floder, String imgName, boolean acl){
		String imgUrl = "";
		try {
			BufferedImage bufferedImage = ImageIO.read(new URL(url));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			return BaiduUpload.baiduUploadImage(is, floder, imgName, acl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgUrl;
	}*/
	
	
	/**
	 * 生成验证码
	 */

	private static final char[] chars = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };
	//字符个数
	private static final int SIZE = 4;
	//干扰线个数
	private static final int LINES = 8;
	//
	private static final int WIDTH = 70;
	//
	private static final int HEIGHT = 28;
	//字体大小
	private static final int FONT_SIZE = 20;

	/**
	 * 随机生成一个图片对象。 返回Map类型，key是图片中的文字， value是图片对象
	 */
	public static Map<String, BufferedImage> createImage() {
		StringBuffer sb = new StringBuffer();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		// 画随机字符
		for (int i = 1; i <= SIZE; i++) {
			int r = ran.nextInt(chars.length);
			graphic.setColor(getRandomColor());
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			graphic.drawString(chars[r] + "", (i - 1) * WIDTH / SIZE,
					HEIGHT - 5);
			sb.append(chars[r]);// 将字符保存，存入Session
		}
		// 画干扰线
		for (int i = 1; i <= LINES; i++) {
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran
					.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.put(sb.toString(), image);
		return map;
	}

	public static Color getRandomColor() {
		Random ran = new Random();
//		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran
//				.nextInt(256));
		Color color = new Color(0,102,153);
		return color;
	}

	/**
	 * 将图片对象转换为输入流
	 * 
	 * @param image
	 *            图片对象
	 * @return 输入流
	 * @throws IOException
	 */
	public static InputStream getInputStream(BufferedImage image)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
		encoder.encode(image);
		byte[] imageBts = bos.toByteArray();
		InputStream in = new ByteArrayInputStream(imageBts);
		return in;
	}
	
	public static void main(String[] args) throws IOException {
		//1、调用组件，生成图片和验证码
		Map<String,BufferedImage> imageMap = 
									ImageUtil.createImage();
		//2、将验证码记录到session，后面验证要用
		String imageCode = 
			imageMap.keySet().iterator().next();
		System.out.println(imageCode);
//		session.put("imageCode", imageCode);
		//3、将生成的图片转换成输入流，赋值给输出属性
		BufferedImage image = imageMap.get(imageCode);
		
		ImageIO.write(image, "jpg", new File("D:\\" + UUID.randomUUID().toString() + ".jpg" ));
	}

	
}

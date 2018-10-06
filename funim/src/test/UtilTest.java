package test;

import org.junit.Test;

public class UtilTest {
	
	@Test
	public void Test1() {
		String str = "name=123&secondName=345&url=6666767&remark=sfsddfsdfsdfasd";
		
		String[] strs = str.split("&");
		
		System.out.println(strs.length);
		
		System.out.println(strs[0].split("=")[1]);
		System.out.println(strs[1].split("=")[1]);
		System.out.println(strs[2].split("=")[1]);
		System.out.println(strs[3].split("=")[1]);
		
//		for (int i = 0; i < strs.length; i++) {
////			System.out.println(strs[i]);
//			String[] keyValue = strs[i].split("=");
//			System.out.println(keyValue[1]);
//		}
		
		
	
		
	}

}

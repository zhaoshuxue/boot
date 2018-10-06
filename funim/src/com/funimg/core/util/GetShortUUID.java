package com.funimg.core.util;

import java.util.UUID;
/**
 * JAVA生成短8位UUID
 * 本算法利用62个可打印字符，通过随机生成32位UUID，
 * 由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，
 * 然后通过模62操作，结果作为索引取出字符，这样重复率大大降低。
 *
 */
public class GetShortUUID {

	public static void main(String[] args) {
//		System.out.println(UUID.randomUUID().toString());// 8-4-4-4-12
//		System.out.println(0x3E);//0x3E == 62
		for (int i = 0; i < 1; i++) {
			System.out.println(generateShortUuid());
		}
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
}

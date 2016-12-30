package com.naruto.booklisting;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-31
 * Time: 0:31
 * Desc: UdaLearn
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTool {
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		is.close();
		bos.flush();
		byte[] result = bos.toByteArray();
		System.out.println(new String(result));
		return result;
	}
}
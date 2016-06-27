package com.wutianyi;

import com.google.common.base.Charsets;
import com.google.common.escape.UnicodeEscaper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.util.List;

/**
 * Created by hanjiewu on 2016/4/14.
 */
public class Example {
	@Test
	public void example() {
		//System.nanoTime()
	}


	public static void main(String[] args) throws IOException {
		String str = "中国人";
		List<String> contents = FileUtils.readLines(new File("F:\\work\\征信\\模板数据\\fm8"));
		CharsetEncoder encode = Charsets.UTF_8.newEncoder();
		char[] cs = new char[1];
		CharBuffer charBuffer = CharBuffer.wrap(cs);
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		for (String content : contents) {
			String[] ts = content.split("\t");
			content = ts[3];
			for (int i = 0; i < content.length(); i++) {
				//System.out.println((int) content.charAt(i));
				System.out.println(Integer.toHexString(content.codePointAt(i)));
				int x = 0x1234;
				//
				//cs[0] = content.charAt(i);
				//charBuffer.clear();
				//byteBuffer.clear();
				//encode.encode(charBuffer, byteBuffer, false);
				//System.out.println(Integer.toHexString((int) cs[0]) + ": " + byteBuffer.position());
			}
		}
	}
}

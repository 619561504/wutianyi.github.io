/**
 * 2015年9月25日hanjiewu
 */
package com.wutianyi.jchardet;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author hanjiewu
 *
 */
public class Example {
	public static void main(String[] args) throws IOException {
		int lang = nsPSMDetector.ALL;
		nsDetector det = new nsDetector(lang);

		det.Init(new nsICharsetDetectionObserver() {

			@Override
			public void Notify(String charset) {
				System.out.println("Charset=" + charset);
			}
		});

		URL url = new URL("http://www.qq.com");
		BufferedInputStream imp = new BufferedInputStream(url.openStream());
		byte[] buf = new byte[1024];
		int len;
		boolean done = false;
		boolean isAscii = true;

		while ((len = imp.read(buf, 0, buf.length)) != -1) {
			if (isAscii) {
				isAscii = det.isAscii(buf, len);
			}

			if (!isAscii && !done) {
				done = det.DoIt(buf, len, false);
			}
		}
		det.DataEnd();
		if (isAscii) {
			System.out.println("Charset = Acsii");
		}
	}
}

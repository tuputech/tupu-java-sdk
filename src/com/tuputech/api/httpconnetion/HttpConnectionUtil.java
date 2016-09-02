package com.tuputech.api.httpconnetion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by soap on 16/7/4. 上传文件并返回结果
 */

public class HttpConnectionUtil {

	/**
	 * 
	 * @param actionUrl
	 *            请求地址
	 * @param secretId
	 *            客户secretId
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param signature
	 *            鉴权信息
	 * @param fileLists
	 *            上传文件
	 * @return
	 */
	public static String uploadImage(String actionUrl, String secretId, String timestamp, String nonce,
			String signature, ArrayList<String> fileLists, String tags[]) {
		String BOUNDARY = UUID.randomUUID().toString();
		Map<String, String> param = new HashMap<String, String>();
		param.put("secretId", secretId);
		param.put("timestamp", timestamp);
		param.put("signature", signature);
		param.put("nonce", nonce);
 		if (tags != null && tags.length > 0) {
			for (int i = 0; i < tags.length; i++) {
				param.put("tag", tags[i]);
			}
		}

		final String PREFIX = "--";
		final String END = "\r\n";
		final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		String result = null;

		try {
			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(15000);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

			/**
			 * 当文件不为空，把文件包装并且上传
			 */
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			StringBuffer stringBuffer = null;
			String params = "";

			/***
			 * 以下是用于上传参数
			 */
			if (param != null && param.size() > 0) {
				Iterator<String> it = param.keySet().iterator();
				while (it.hasNext()) {
					stringBuffer = null;
					stringBuffer = new StringBuffer();
					String key = it.next();
					String value = param.get(key);
					stringBuffer.append(PREFIX).append(BOUNDARY).append(END);
					stringBuffer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(END)
							.append(END);
					stringBuffer.append(value).append(END);
					params = stringBuffer.toString();
					dos.write(params.getBytes());
				}
			}
			for (int i = 0; i < fileLists.size(); i++) {

				String uploadFile = fileLists.get(i);
				dos.writeBytes(PREFIX + BOUNDARY + END);
				dos.writeBytes("Content-Disposition:form-data; name=\"" + "image" + "\"; filename=\"" + fileLists.get(i)
						+ "\"" + END);
				dos.writeBytes("Content-Type:image/pjpeg" + END);
				dos.writeBytes(END);
				FileInputStream fStream = new FileInputStream(uploadFile);
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				int length = -1;
				while ((length = fStream.read(buffer)) != -1) {
					dos.write(buffer, 0, length);
				}
				dos.writeBytes(END);
				fStream.close();
			}

			dos.write(END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + END).getBytes();
			dos.write(end_data);
			dos.flush();
			int res = conn.getResponseCode();
			if (res == 200) {
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();

			} else {
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * URL 方式测试文件
	 * 
	 * @param actionUrl
	 *            请求路径
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 * @param signature
	 *            鉴权信息
	 * @param fileLists
	 *            文件url 列表
	 * @return
	 */
	public static String uploadUri(String actionUrl, String timestamp, String nonce, String signature,
			ArrayList<String> fileLists, String tags[]) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String httpArg = "timestamp=" + timestamp + "&nonce=" + nonce + "&signature=" + URLEncoder.encode(signature);
		if (tags != null && tags.length > 0) {
			for (int i = 0; i < tags.length; i++) {
				httpArg += "&tag=" + tags[i];
			}
		}
		if (fileLists.size() > 0) {
			for (int i = 0; i < fileLists.size(); i++) {
				httpArg += "&image=" + URLEncoder.encode(fileLists.get(i));
			}
			// 如果采用直接post文件的方式，则将file文件添加至参数中
		}
		try {
			URL connect_url = new URL(actionUrl);
			HttpURLConnection connection = (HttpURLConnection) connect_url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setDoOutput(true);
			connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

package com.soap.api;

import java.util.ArrayList;

import com.tuputech.api.Api;
import com.tuputech.api.util.ConfigUtil;

import net.sf.json.JSONObject;

public class APITest {

	public static void main(String[] args) {
		// secret id
		String secretId = "";
		// private KEY path
		String privateKey = "";
		// fileList imageFile or url
		ArrayList<String> fileList = new ArrayList<String>();
		// tags
		String tags[] = { "1111111", "222222" };
		fileList.add("pem/1.png");
		fileList.add("pem/2.jpg");
		fileList.add("pem/4.png");
		fileList.add("pem/3.jpg");
		fileList.add("pem/5.png");
		fileList.add("pem/7.png");
		fileList.add("pem/6.png");
		fileList.add("pem/test.jpg");

		Api api = new Api(secretId, privateKey);

		JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileList);

		System.out.println(result);
	}
}

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
		String tags[] = { "tag1", "tag2" };
		
		fileList.add("../test1.jpg");
		fileList.add("../test2.jpg");

		Api api = new Api(secretId, privateKey);

		JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileList,tags);

		System.out.println(result);
	}
}

package com.tuputech.api;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.tuputech.api.Api;
import com.tuputech.api.model.Options;
import com.tuputech.api.util.ConfigUtil;

public class APITest {

	public static void main(String[] args) {
		// secret id
		String secretId = "your_secret_id";
		// private KEY path
		String privateKey = "your_private_key_path";
		// request Url
		String requestUrl = "http://api.open.tuputech.com/v3/recognition/";
		// fileList imageFile or url
		ArrayList<String> fileLists = new ArrayList<String>();
		fileLists.add("../test1.jpg");
		fileLists.add("../test2.jpg");
		// tags
		String tags[] = { "tag1", "tag2" };
		// options
		Options options = new Options();
		options.setTags(tags);
		// options.setUid("your_uid"); // 第三方客户标识
		
		Api api = new Api(secretId, privateKey, requestUrl);
		JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileLists, options);

		System.out.println(result);
	}
}

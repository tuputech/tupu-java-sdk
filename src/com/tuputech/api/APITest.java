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
		// fileList imageFile or url
		ArrayList<String> fileList = new ArrayList<String>();
		// tags
		String tags[] = { "tag1", "tag2" };
		String[] fileLists = {
				"img_url_1", "img_url_2" };
		
		for (int i = 0; i < fileLists.length; i++) {
			fileList.add(fileLists[i]);
		}
		Api api = new Api(secretId, privateKey,null);
		Options options = new Options();
		options.setTags(tags);
//		options.setUid("your_uid"); // 第三方客户标识
		JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE, fileList, options);

		System.out.println(result);
	}
}

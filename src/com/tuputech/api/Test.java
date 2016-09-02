package com.tuputech.api;

import java.util.ArrayList;

import com.tuputech.api.util.ConfigUtil;

public class Test {

	public static void main(String[] args) {
		String secretId = "57a406868b232e52177d68f9";

		String privateKey = "pem/pkcs8_private_key.pem";
		String tags [] = {"1111111111","22222222"};
		 ArrayList<String> image=  new ArrayList<String>();
//		 image.add("pem/1.png");
//		 image.add("pem/2.jpg");
//		 image.add("pem/4.png");
//		 image.add("pem/3.jpg");
//		 image.add("pem/5.png");
//		 image.add("pem/7.png");
//		 image.add("pem/6.png");
		 
		 image.add("http://i.mmcdn.cn/simba/img/TB1Z465HFXXXXceXVXXSutbFXXX.jpg");
		 image.add("http://i.mmcdn.cn/simba/img/TB1Z465HFXXXXceXVXXSutbFXXX.jpg");
		 image.add("https://www.tuputech.com/data/5714950723d7718d2bbffd81/2015-04-23/9X3IhpGJT6IpPHR1zan_Ow==.resize");
		 
		Api api = new Api(secretId, privateKey);

		String result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE,tags, image);

		System.out.println(result);
	}
}

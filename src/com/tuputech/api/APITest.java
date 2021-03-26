package com.tuputech.api;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.tuputech.api.model.Options;
import com.tuputech.api.util.ConfigUtil;

public class APITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path
        String privateKey = "";
        // request Url
        String requestUrl = "http://api.open.tuputech.com/v3/recognition/";
        // fileList imageFile or url
        ArrayList<String> fileLists = new ArrayList<String>();
         fileLists.add("../test.jpg");

        // fileLists.add("../test2.jpg");

        // options
        String tags[] = { "tag1", "tag2" };
        Options options = new Options();
        options.setTags(tags);

        // http timeout config
        options.setConnectTimeout(16000);
        options.setReadTimeout(16000);

        Api api = new Api(secretId, privateKey, requestUrl);
        JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileLists, options);

        System.out.println(result);
    }
}

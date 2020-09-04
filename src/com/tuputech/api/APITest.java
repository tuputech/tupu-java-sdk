package com.tuputech.api;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.tuputech.api.model.Options;
import com.tuputech.api.util.ConfigUtil;

public class APITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "5f506b980adde3001e669881";
        // private KEY path
        String privateKey = "./pem/pkcs8_private_key.pem";
        // request Url
        String requestUrl = "http://api.open.tuputech.com/v3/recognition/";
        // fileList imageFile or url
        ArrayList<String> fileLists = new ArrayList<String>();
        fileLists.add("https://tp_annotation.tuputech.com/file/world/data-gpu-16/ARG/customer_data/milian_2020.8.3-8.6/15965025344020.782813140047121.wav");
        fileLists.add("https://tp_annotation.tuputech.com/file/world/data-gpu-16/ARG/customer_data/milian_2020.8.3-8.6/15965323965200.7081625723877469.wav");

        // fileLists.add("../test2.jpg");

        // options
        String tags[] = { "tag1", "tag2" };
        Options options = new Options();
        options.setTags(tags);

        // http timeout config
        options.setConnectTimeout(16000);
        options.setReadTimeout(16000);

        Api api = new Api(secretId, privateKey, requestUrl);
        JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE, fileLists, options);

        System.out.println(result);
    }
}

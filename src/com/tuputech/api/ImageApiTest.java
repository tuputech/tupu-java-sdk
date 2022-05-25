package com.tuputech.api;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.tuputech.api.model.Options;
import com.tuputech.api.util.ConfigUtil;

public class ImageApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String requestUrl = "http://api-oversea.open.tuputech.com/v3/recognition/";
        // fileList imageFile or url
        ArrayList<String> fileLists = new ArrayList<String>();
        fileLists.add("");


        Options options = new Options();
        // http timeout config
        options.setConnectTimeout(16000);
        options.setReadTimeout(16000);

        Api api = new Api(secretId, privateKey, requestUrl);
        JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileLists, options);

        System.out.println(result);
    }
}

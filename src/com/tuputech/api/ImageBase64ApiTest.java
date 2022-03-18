package com.tuputech.api;

import com.tuputech.api.model.ImageBase64;
import com.tuputech.api.util.ConfigUtil;
import com.tuputech.api.util.ImageBase64Converter;
import net.sf.json.JSONObject;

public class ImageBase64ApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String requestUrl = ConfigUtil.NET_WORK.IMAGE_BASE64_URL;
        String imageUrl="";
        ImageBase64 imageBase64 = new ImageBase64();

        String imgBase64Str= ImageBase64Converter.convertFileToBase64(imageUrl);
        String[] images = {imgBase64Str};

        imageBase64.setImages(images);
        ImageBase64Api api = new ImageBase64Api(secretId, privateKey, requestUrl);
        JSONObject result = api.doImageBase64Api(imageBase64);

        System.out.println(result);
    }
}

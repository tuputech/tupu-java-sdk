package com.tuputech.api;

import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

/**
 * 异步视频获取结果API
 */
public class SpeechAsyncResultApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String videoApiUrl = ConfigUtil.NET_WORK.SPEECH_ASYNC_RESULT_API_URI;

        String requestId ="";


        SpeechAsyncResultApi api = new SpeechAsyncResultApi(secretId, privateKey, videoApiUrl);
        //获取异步语音结果
        JSONObject result = api.getSpeechResult(requestId);
        System.out.println("do speech result is : " + result);

    }
}

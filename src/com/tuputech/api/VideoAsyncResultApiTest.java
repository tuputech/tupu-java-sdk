package com.tuputech.api;

import com.tuputech.api.model.VideoAsync;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

/**
 * 异步视频获取结果API
 */
public class VideoAsyncResultApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url  区分国内国外 国外请使用 ConfigUtil.NET_WORK.VIDEO_ASYNC_RESULT_API_ABROAD_URI
        String videoApiUrl = ConfigUtil.NET_WORK.VIDEO_ASYNC_RESULT_API_URI;

        String videoId ="";


        VideoAsyncResultApi api = new VideoAsyncResultApi(secretId, privateKey, videoApiUrl);
        //获取异步视频结果
        JSONObject result = api.getVideoResult(videoId);
        System.out.println("do video result is : " + result);

    }
}

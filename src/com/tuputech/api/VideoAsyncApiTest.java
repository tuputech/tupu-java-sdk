package com.tuputech.api;

import com.tuputech.api.model.Options;
import com.tuputech.api.model.Video;
import com.tuputech.api.model.VideoAsync;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

/**
 * 异步视频测试API
 */
public class VideoAsyncApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String videoApiUrl = ConfigUtil.NET_WORK.VIDEO_ASYNC_STREAM_API_URI;

        VideoAsync videoAsync = new VideoAsync();

        //视频截图间隔，范围[1, 10]秒，默认3秒。
        videoAsync.setInterval(3);
        //回调地址，用于通知返回结果，支持HTTP(S)。
        videoAsync.setCallbackUrl("");
        //文件地址
        videoAsync.setVideo("");


        VideoAsyncApi api = new VideoAsyncApi(secretId, privateKey, ConfigUtil.VIDEO_UPLOAD_TYPE.UPLOAD_VIDEO_STREAM_TYPE, videoApiUrl);
        //执行异步视频检测
        JSONObject result = api.doVideoASyncApi(videoAsync);
        System.out.println("do video result is : " + result);
//
        String videoId = "";
        //关闭异步视频
        JSONObject clouseResult = api.closeSpeechApi(videoId);
        System.out.println("do video result is : "+ clouseResult);


    }
}

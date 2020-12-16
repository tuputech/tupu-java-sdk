package com.tuputech.api;

import com.tuputech.api.model.Options;
import com.tuputech.api.model.Video;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;


public class VideoAPITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path
        String privateKey = "";
        // request Url
        String videoApiUrl = ConfigUtil.NET_WORK.SYNC_VIDEO_API_URI;

        Video video = new Video();
        //视频截图间隔，范围[1, 10]秒，默认1秒。
        video.setInterval(2);
        //最大截图张数（达到最大截图张数则结束截图），范围[5, 200]张，默认200张。
        video.setMaxFrames(10);
        //文件地址
        video.setVideo("");

        // options
        Options options = new Options();
        // http timeout config
        options.setConnectTimeout(2*60*1000);
        //读取超时时间
        options.setReadTimeout(2*60*1000);
        //房间名
        options.setTag("房间名");


        VideoApi api = new VideoApi(secretId, privateKey, videoApiUrl);
        //执行同步视频检测
        JSONObject result = api.doVideoSyncApi(video, options);
        System.out.println("do video result is : "+ result);


    }
}

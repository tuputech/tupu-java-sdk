package com.tuputech.api;

import com.tuputech.api.model.RecordingFile;
import com.tuputech.api.model.VideoAsync;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

/**
 * 异步语音测试API
 */
public class SpeechAsyncApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String url = ConfigUtil.NET_WORK.SPEECH_ASYNC_API_URI;

        RecordingFile file = new RecordingFile();

        file.setUrl("");
        file.setRoomId("");
        file.setUserId("");
        file.setForumId("");
        file.setCallbackUrl("");
        SpeechAsyncApi api = new SpeechAsyncApi(secretId, privateKey, url);
        //执行异步视频检测
        JSONObject result = api.doSpeechASyncApi(file);
        System.out.println("do speech result is : " + result);

    }
}

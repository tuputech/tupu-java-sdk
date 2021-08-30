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
        //录音文件信息，详见下面说明
        RecordingFile file = new RecordingFile();
        //音频文件地址；大小限制：100M 以内；允许识别的格式：amr, mp3, wmv, wav, flv, m4a
        file.setUrl("");
        //检测结果的回调地址（支持http, https）。失败重试 2 次，需自行保证回调地址正常
        file.setCallbackUrl("");

        //不传值表示出现违规才回调；all 表示回调全部结果
        //        file.setCallbackRule("");
        //房间 ID
        //        file.setRoomId("");
        //用户 ID
        //        file.setUserId("");
        //板块 ID ，数据 ID
        //        file.setForumId("");
        //自定义信息，用于给请求附加额外信息（比如：关联的信息）。
        //        file.setCustomInfo();

        SpeechAsyncApi api = new SpeechAsyncApi(secretId, privateKey, url);
        //执行异步语音检测
        JSONObject result = api.doSpeechASyncApi(file);
        System.out.println("do speech result is : " + result);

    }
}

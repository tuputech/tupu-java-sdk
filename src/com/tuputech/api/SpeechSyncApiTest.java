package com.tuputech.api;

import com.tuputech.api.model.Options;
import com.tuputech.api.model.Video;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;


public class SpeechSyncApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String url = ConfigUtil.NET_WORK.SPEECH_SYNC_API_URI;

        //支持传语音文件或者URl,不过两者不能混在同一请求中；大小限制：1M以内；允许识别的格式：amr, mp3, wmv, wav, flv, m4a
        String[] speech = {""};

        //指定本次调用要审核的任务，任务id的形式，如：5caee6b2a76925c55a09a6d2
        String taskId = "";

        // url or file 支持传语音文件或者URl
        String speechType = "file";


        SpeechSyncApi api = new SpeechSyncApi(secretId, privateKey, url);
        //执行同步语音检测
        JSONObject result = api.doSpeechSyncApi(speech, taskId, speechType);
        System.out.println("do speech result is : " + result);

    }
}

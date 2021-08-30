package com.tuputech.api;

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

        String[] speech = {""};

        //指定运行的任务 ID
        String taskId ="";

        // url or file;
        String speechType = "file";



        SpeechSyncApi api = new SpeechSyncApi(secretId, privateKey, url);
        //执行同步视频检测
        JSONObject result = api.doSpeechSyncApi(speech, taskId,speechType);
        System.out.println("do speech result is : "+ result);


    }
}

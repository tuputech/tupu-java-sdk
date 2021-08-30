package com.tuputech.api;

import com.tuputech.api.model.Options;
import com.tuputech.api.model.SpeechStream;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class SpeechStreamApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String speechApiUrl = ConfigUtil.NET_WORK.SPEECH_API_URI;

        String closeSpeechUrl = ConfigUtil.NET_WORK.SPEECH_API_URI_CLOSE;

        ArrayList<SpeechStream> speechStreamLists = new ArrayList();
        SpeechStream speechStream = new SpeechStream();
        //检测结果的回调地址
        speechStream.setCallbackUrl("");
        //音频流地址
        speechStream.setUrl("");
        speechStreamLists.add(speechStream);

        //指定运行的任务 ID 列表
        String tasks[] = { "taskId1", "taskId2" };



        // options
        Options options = new Options();
        // http timeout config
        options.setConnectTimeout(16000);
        options.setReadTimeout(16000);
        options.setTasks(tasks);

        SpeechStreamApi api = new SpeechStreamApi(secretId, privateKey, speechApiUrl, closeSpeechUrl);
        //执行语音检测
        JSONObject result = api.doSpeechApi(speechStreamLists, options);
//        System.out.println("do speech result is : "+ result);


        ArrayList<String> requestIdLists = new ArrayList();
//        requestIdLists.add("5f50bd5ecd0c02001e843433");
        //添加 客户提交的音频流的唯一请求id，后续可以通过该id查到音频的相关信息
        requestIdLists.add("");
        //关闭 id 对应的语音检测
        JSONObject closeResult = api.closeSpeechApi(requestIdLists, options);
        System.out.println("do speech close result is : " + closeResult);

    }
}

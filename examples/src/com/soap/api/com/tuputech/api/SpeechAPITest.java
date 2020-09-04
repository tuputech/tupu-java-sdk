package com.tuputech.api;

import com.tuputech.api.model.Options;
import com.tuputech.api.model.SpeechStream;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class SpeechAPITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path
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

        // options
        Options options = new Options();
        // http timeout config
        options.setConnectTimeout(16000);
        options.setReadTimeout(16000);


        SpeechApi api = new SpeechApi(secretId, privateKey, speechApiUrl, closeSpeechUrl);
        //执行语音检测
        JSONObject result = api.doSpeechApi(speechStreamLists, options);
//        System.out.println("do speech result is : "+ result);


        ArrayList<String> requestIdLists = new ArrayList();
        //添加 客户提交的音频流的唯一请求id，后续可以通过该id查到音频的相关信息
        requestIdLists.add("");
        //关闭 id 对应的语音检测
        JSONObject closeResult = api.closeSpeechApi(requestIdLists, options);
        System.out.println("do speech close result is : " + closeResult);

    }
}

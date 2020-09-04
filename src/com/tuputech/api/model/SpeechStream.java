package com.tuputech.api.model;

/**
 * @author soap
 * @date 2020-09-03
 * 语音识别 Options
 */
public class SpeechStream {
    //音频流地址
    private String url;
    //检测结果的回调地址
    private String callbackUrl;
    //房间ID
    private String roomId;
    //用户ID
    private String userId;
    //板块ID ，数据ID
    private String formId;
    //检测结果的回调规则, 默认为0, 0: 只回调违规数据 1: 回调所有审核数据 , 语音转译会回调所有译文数据
    private int callbackRules = 0;
    //表示需要返回违规音频前一分钟（含违规音频，最长为一分钟）音频链接； 值为false时，只需返回违规片段音频链接。默认值为false
    private boolean returnPreSpeech = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCallbackRules() {
        return callbackRules;
    }

    public void setCallbackRules(int callbackRules) {
        this.callbackRules = callbackRules;
    }

    public boolean isReturnPreSpeech() {
        return returnPreSpeech;
    }

    public void setReturnPreSpeech(boolean returnPreSpeech) {
        this.returnPreSpeech = returnPreSpeech;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}

package com.tuputech.api.model;


import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author soap
 * @date 2020-12-15
 * 异步语音识别RecordingFile
 */
public class RecordingFile {

    //音频文件地址；大小限制：100M 以内；允许识别的格式：amr, mp3, wmv, wav, flv, m4a
    private String url;
    //检测结果的回调地址（支持http, https）。失败重试 2 次，需自行保证回调地址正常
    private String callbackUrl;
    //不传值表示出现违规才回调；all 表示回调全部结果
    private String callbackRule;
    //房间 ID
    private String roomId;
    //用户 ID
    private String userId;
    //板块 ID ，数据 ID
    private String forumId;
    //自定义信息，用于给请求附加额外信息（比如：关联的信息）。
    private Map<String, String> customInfo;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackRule() {
        return callbackRule;
    }

    public void setCallbackRule(String callbackRule) {
        this.callbackRule = callbackRule;
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

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public Map<String, String> getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(Map<String, String> customInfo) {
        this.customInfo = customInfo;
    }
}

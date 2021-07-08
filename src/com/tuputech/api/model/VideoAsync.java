package com.tuputech.api.model;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public class VideoAsync {

    //1.支持视频链接或者上传视频文件；
    //2.只支持一个video参数，即只能传一个视频链接或者上传一个视频文件；
    //3.支持常见视频格式和gif动图
    private String video;
    //自定义信息，用于给视频附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）
    private Map<String, String> customInfo;
    //视频截图间隔，范围[1, 10]秒，默认3秒。
    private int interval = 3;
    //视频片段时长，范围[10, 60]秒，默认60秒。
    private int fragmentTime = 60;
    //回调地址，用于通知返回结果，支持HTTP(S)。
    private String callbackUrl;
    //回调规则，用于返回指定类型的数据，默认返回所有类型的数据。
    private List<VideoTask> callbackRules;
    //是否实时回调，用于实时回调视频片段结果，默认false。
    private boolean realTimeCallback = false;
    //是否开启语音识别，使用该功能请联系我们，默认false。
    private boolean audio = false;
    //指定本次调用要审核的任务。
    private String[] task;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Map<String, String> getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(Map<String, String> customInfo) {
        this.customInfo = customInfo;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getFragmentTime() {
        return fragmentTime;
    }

    public void setFragmentTime(int fragmentTime) {
        this.fragmentTime = fragmentTime;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public List<VideoTask> getCallbackRules() {
        return callbackRules;
    }

    public void setCallbackRules(List<VideoTask> callbackRules) {
        this.callbackRules = callbackRules;
    }

    public boolean isRealTimeCallback() {
        return realTimeCallback;
    }

    public void setRealTimeCallback(boolean realTimeCallback) {
        this.realTimeCallback = realTimeCallback;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public String[] getTask() {
        return task;
    }

    public void setTask(String[] task) {
        this.task = task;
    }
}

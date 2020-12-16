package com.tuputech.api.model;

/**
 * @author soap
 * @date 2020-12-15
 * 视频识别 Options
 */
public class Video {
    //1.支持视频链接或者上传视频文件；
    //2.只支持一个video参数，即只能传一个视频链接或者上传一个视频文件；
    //3.支持常见视频格式和gif动图
    private String video;
    //视频截图间隔，范围[1, 10]秒，默认1秒。
    private int interval=1;
    //最大截图张数（达到最大截图张数则结束截图），范围[5, 200]张，默认200张。
    private int maxFrames=200;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(int maxFrames) {
        this.maxFrames = maxFrames;
    }
}

package com.tuputech.api.model;

public class ImageBase64 {
    //1、图片列表，列表元素内容为图片base64内容；
    //2、支持的图片格式：png、jpg、jpeg、tif、webp、bmp、gif；
    //3、为了加快网络传输速度，图片大小必须在1M以内，且建议对图片进行压缩处理，等比压缩到[256, 512]之间，在进行base64编码
    private String[] images;
    //用于给图片或文本附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）。方便后续根据tag搜索到相关的图片或文本。
    //1、tags数目和images参数一一对应；
    //2、如果tags只有一个，或者tags数量少于image或text，缺省用最后一个tag补全（尾补全）。
    private String[] tags;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTasks() {
        return tasks;
    }

    public void setTasks(String[] tasks) {
        this.tasks = tasks;
    }

    //如果需要只执行接口下的某些审核的任务，可通过此参数指定，任务id的形式，如：['54bcfc6c329af61034f7c2fc']。不传该参数的话，执行该接口下的全部审核任务。
    private String[] tasks;
}

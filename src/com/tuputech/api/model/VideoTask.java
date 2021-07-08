package com.tuputech.api.model;

public class VideoTask {
    //指定返回的识别分类
    private int label;
    //指定返回的复审类型。
    private boolean review;
    //是否返回图片的截帧位置，默认true。
    private boolean offset = true;
    //是否返回图片搜索的人物的全量图片，默认true。
    private boolean total = true;
    //指定图片搜索返回的人物名称。
    private String[] faceId;
    //指定图片搜索返回的人物类型。
    private String[] typeName;
    //指定图片搜索返回大于等于相似度的结果。
    private float similarity;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
    }

    public boolean isOffset() {
        return offset;
    }

    public void setOffset(boolean offset) {
        this.offset = offset;
    }

    public boolean isTotal() {
        return total;
    }

    public void setTotal(boolean total) {
        this.total = total;
    }

    public String[] getFaceId() {
        return faceId;
    }

    public void setFaceId(String[] faceId) {
        this.faceId = faceId;
    }

    public String[] getTypeName() {
        return typeName;
    }

    public void setTypeName(String[] typeName) {
        this.typeName = typeName;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
}

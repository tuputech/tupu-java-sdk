package com.tuputech.api.model;

public class Options {

    private String[] tags;
    private String uid = null;

    private int readTimeout = 15000;
    private int connectTimeout = 15000;

    public Options() {

    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.connectTimeout = timeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.readTimeout = timeout;
    }

    public Options(String[] tags, String uid) {
        this.tags = tags;
        this.uid = uid;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}

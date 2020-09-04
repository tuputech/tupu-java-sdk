package com.tuputech.api.httpconnetion.model;

import com.tuputech.api.model.SpeechStream;
import net.sf.json.JSONArray;

import java.util.ArrayList;

public class SpeechRequest {
    private JSONArray speechStream;
    private String timestamp;
    private String signature;
    private String nonce;

    public JSONArray getSpeechStream() {
        return speechStream;
    }

    public void setSpeechStream(JSONArray speechStream) {
        this.speechStream = speechStream;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "{" +
                "speechStream:" + speechStream +
                ", timestamp:" + timestamp +
                ", signature:'" + signature + '\'' +
                ", nonce:" + nonce +
                '}';
    }
}

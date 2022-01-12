package com.tuputech.api;

import com.tuputech.api.httpconnetion.HttpConnectionUtil;
import com.tuputech.api.model.ClassificationResult;
import com.tuputech.api.model.Options;
import com.tuputech.api.model.SpeechStream;
import com.tuputech.api.util.ConfigUtil;
import com.tuputech.api.util.ErrorUtil;
import com.tuputech.api.util.SignatureAndVerifyUtil;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * @author soap
 * 语音流识别,API
 */

public class SpeechStreamApi {
    private String secretId;
    private String url;
    private PrivateKey privateKey;
    private String urlClose;

    /**
     * @param secretId       用户secretId
     * @param pkPath         用户私钥
     * @param speechUrl      请求接口地址
     * @param speechUrlClose 关闭请求接口地址
     */
    public SpeechStreamApi(String secretId, String pkPath, String speechUrl, String speechUrlClose) {
        if (null == speechUrl) {
            speechUrl = ConfigUtil.NET_WORK.SPEECH_API_URI;
        }
        if (null == speechUrlClose) {
            speechUrlClose = ConfigUtil.NET_WORK.SPEECH_API_URI_CLOSE;

        }
        this.secretId = secretId;
        this.url = speechUrl + secretId;
        this.urlClose = speechUrlClose + secretId;
        this.privateKey = SignatureAndVerifyUtil.readPrivateKey(pkPath);

    }

    /**
     * @param speechStreamLists 文件流地址集合
     * @return
     */
    public JSONObject doSpeechApi(ArrayList<SpeechStream> speechStreamLists, Options options) {
        if (speechStreamLists == null || speechStreamLists.isEmpty()) {
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
        }
        long timestamp = Math.round(System.currentTimeMillis() / 1000.0);
        double nonce = Math.random();
        String sign_string = secretId + "," + timestamp + "," + nonce;

        String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);

        ClassificationResult classificationResult = null;

        try {
            // 返回网络请求数据
            classificationResult = HttpConnectionUtil.uploadSpeechUri(url, timestamp, nonce, signature, speechStreamLists,
                    options);

            return getResult(classificationResult);
        } catch (Exception e) {
            System.out.println("TUPU API: response verify failed, error is " + e.getMessage());
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_OTHERS, e.getMessage());
        }
    }

    /**
     * @param requestIdLists 请求requestId集合
     * @return
     */
    public JSONObject closeSpeechApi(ArrayList<String> requestIdLists, Options options) {
        if (requestIdLists == null || requestIdLists.isEmpty()) {
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
        }
        long timestamp = Math.round(System.currentTimeMillis() / 1000.0);
        double nonce = Math.random();
        String sign_string = secretId + "," + timestamp + "," + nonce;
        String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);
        ClassificationResult classificationResult = null;

        try {
            // 返回网络请求数据
            classificationResult = HttpConnectionUtil.closeSpeechStream(urlClose, timestamp, nonce, signature, requestIdLists,
                    options);

            return getSpeechCloseResult(classificationResult);
        } catch (Exception e) {

            System.out.println("TUPU API: response verify failed, error is " + e.getMessage());
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_OTHERS, e.getMessage());
        }
    }

    /**
     * 处理接口结果
     *
     * @param classificationResult 网络请求数据
     * @return
     * @throws UnsupportedEncodingException
     */
    private JSONObject getResult(ClassificationResult classificationResult) throws UnsupportedEncodingException {

        if (classificationResult.getResultCode() == 200) {
            String result = classificationResult.getResult();

            JSONObject jsonObject = JSONObject.fromObject(result);

            String result_json = jsonObject.getString("json");
            String code = JSONObject.fromObject(result_json).getString("code");
            String message = JSONObject.fromObject(result_json).getString("message");
            if (Integer.valueOf(code) == 0) {
                String result_signature = jsonObject.getString("signature");
                // 进行验证
                boolean verify = SignatureAndVerifyUtil.Verify(result_signature, result_json);

                if (verify) {
                    System.out.println("TUPU API: response verify succeed, result is :" + result_json);
                    return JSONObject.fromObject(result_json);
                } else {
                    System.out.println("TUPU API: response verify failed,error is : 102:结果验证失败");
                    return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_RESULT_VERIFY_FAILED, "");
                }
            } else {
                System.out.println("TUPU API: response verify failed, error is : " + ErrorUtil.getErrorMsg(Integer.valueOf(code), message));
                return ErrorUtil.getErrorMsg(Integer.valueOf(code), message);
            }
        } else {
            System.out.println("TUPU API: response verify failed, error is : " + ErrorUtil.getErrorMsg(classificationResult.getResultCode(), ""));
            return ErrorUtil.getErrorMsg(classificationResult.getResultCode(), "");
        }
    }

    /**
     * 关闭语音流结果解析
     *
     * @param classificationResult
     * @return
     * @throws UnsupportedEncodingException
     */
    private JSONObject getSpeechCloseResult(ClassificationResult classificationResult) throws UnsupportedEncodingException {

        if (classificationResult.getResultCode() == 200) {
            String result = classificationResult.getResult();

            JSONObject jsonObject = JSONObject.fromObject(result);
            String result_json = jsonObject.getString("json");
            String result_signature = jsonObject.getString("signature");
            // 进行验证
            boolean verify = SignatureAndVerifyUtil.Verify(result_signature, result_json);
            if (verify) {
                System.out.println("TUPU API: response verify succeed, result is :" + result_json);
                return JSONObject.fromObject(result_json);
            } else {
                System.out.println("TUPU API: response verify failed,error is : 102:结果验证失败");
                return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_RESULT_VERIFY_FAILED, "");
            }

        } else {
            System.out.println("TUPU API: response verify failed, error is : " + ErrorUtil.getErrorMsg(classificationResult.getResultCode(), ""));
            return ErrorUtil.getErrorMsg(classificationResult.getResultCode(), "");
        }
    }
}
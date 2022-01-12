package com.tuputech.api;

import com.tuputech.api.httpconnetion.SpeechHttpConnectionUtil;
import com.tuputech.api.model.ClassificationResult;
import com.tuputech.api.model.RecordingFile;
import com.tuputech.api.util.ConfigUtil;
import com.tuputech.api.util.ErrorUtil;
import com.tuputech.api.util.SignatureAndVerifyUtil;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;

/**
 * @author soap
 * @date 2020-12-15
 * 异步语音调用Api
 */
public class SpeechAsyncApi {
    private String secretId;
    private String url;
    private PrivateKey privateKey;


    /**
     * @param secretId 用户secretId
     * @param pkPath   用户私钥
     * @param speechUrl 请求接口地址
     */
    public SpeechAsyncApi(String secretId, String pkPath, String speechUrl) {
        if (null == speechUrl) {
            speechUrl =  ConfigUtil.NET_WORK.SPEECH_ASYNC_API_URI;
        }
        this.secretId = secretId;
        this.url = speechUrl + secretId;
        this.privateKey = SignatureAndVerifyUtil.readPrivateKey(pkPath);

    }

    /**
     * @param speech 文件
     * @return
     */
    public JSONObject doSpeechASyncApi(RecordingFile speech) {
        if (speech == null) {
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
        }
        long timestamp = Math.round(System.currentTimeMillis() / 1000.0);
        double nonce = Math.random();
        String sign_string = secretId + "," + timestamp + "," + nonce;

        String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);

        ClassificationResult classificationResult = null;

        try {
            // 返回网络请求数据
            classificationResult = SpeechHttpConnectionUtil.uploadSpeechAsync(url, String.valueOf(timestamp), String.valueOf(nonce), signature, speech);

            return getResult(classificationResult);
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
}
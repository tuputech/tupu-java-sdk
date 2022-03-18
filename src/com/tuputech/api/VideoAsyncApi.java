package com.tuputech.api;

import com.tuputech.api.httpconnetion.HttpConnectionUtil;
import com.tuputech.api.model.ClassificationResult;
import com.tuputech.api.model.Options;
import com.tuputech.api.model.Video;
import com.tuputech.api.model.VideoAsync;
import com.tuputech.api.util.ConfigUtil;
import com.tuputech.api.util.ErrorUtil;
import com.tuputech.api.util.SignatureAndVerifyUtil;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * @author soap
 * @date 2020-12-15
 * 视频调用Api
 */
public class VideoAsyncApi {
    private String secretId;
    private String url;
    private PrivateKey privateKey;
    private String urlClose = "http://api.open.tuputech.com/v3/recognition/video/close/";
    private String videoType;

    /**
     * @param secretId 用户secretId
     * @param pkPath   用户私钥
     * @param videoUrl 请求接口地址
     */
    public VideoAsyncApi(String secretId, String pkPath, String videoType, String videoUrl) {
        if (null == videoUrl) {
            videoUrl = videoType.equals(ConfigUtil.VIDEO_UPLOAD_TYPE.UPLOAD_VIDEO_STREAM_TYPE) ? ConfigUtil.NET_WORK.VIDEO_ASYNC_STREAM_API_URI : ConfigUtil.NET_WORK.VIDEO_ASYNC_FILE_API_URI;
        }
        this.videoType =videoType;
        this.secretId = secretId;
        this.url = videoUrl + secretId;
        this.privateKey = SignatureAndVerifyUtil.readPrivateKey(pkPath);

    }

    /**
     * @param video 文件
     * @return
     */
    public JSONObject doVideoASyncApi(VideoAsync video) {
        if (video == null) {
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
        }
        String timestamp = String.valueOf(Math.round(System.currentTimeMillis() / 1000.0));
        String  nonce = String.valueOf(Math.random());
        String sign_string = secretId + "," + timestamp + "," + nonce;

        String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);

        ClassificationResult classificationResult = null;

        try {
            // 返回网络请求数据
            classificationResult = HttpConnectionUtil.uploadVideoAsync(url, timestamp, nonce, signature, video,
                     videoType);

            return getResult(classificationResult);
        } catch (Exception e) {
            System.out.println("TUPU API: response verify failed, error is " + e.getMessage());
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_OTHERS, e.getMessage());
        }
    }
    /**
     * @param videoId
     * @return
     */
    public JSONObject closeSpeechApi(String videoId) {
        if (videoId == null || videoId.isEmpty()) {
            return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
        }
        long timestamp = Math.round(System.currentTimeMillis() / 1000.0);
        double nonce = Math.random();
        String sign_string = secretId + "," + timestamp + "," + nonce;
        String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);
        ClassificationResult classificationResult = null;
        urlClose =urlClose+secretId;
        try {
            // 返回网络请求数据
            classificationResult = HttpConnectionUtil.closeVideoAsync(urlClose, timestamp, nonce, signature, videoId);

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
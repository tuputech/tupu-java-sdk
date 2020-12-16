package com.tuputech.api.httpconnetion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.tuputech.api.httpconnetion.model.SpeechRequest;
import com.tuputech.api.model.ClassificationResult;
import com.tuputech.api.model.Options;
import com.tuputech.api.model.SpeechStream;
import com.tuputech.api.model.Video;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by soap on 16/7/4. 上传文件并返回结果
 */

public class HttpConnectionUtil {

    /**
     * @param actionUrl 请求地址
     * @param secretId  客户secretId
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 鉴权信息
     * @param fileLists 上传文件
     * @return
     */
    public static ClassificationResult uploadImage(String actionUrl, String secretId, String timestamp, String nonce,
                                                   String signature, ArrayList<String> fileLists, Options options) throws Exception {
        String BOUNDARY = UUID.randomUUID().toString();
        Map<String, String> param = new HashMap<String, String>();
        param.put("secretId", secretId);
        param.put("timestamp", timestamp);
        param.put("signature", signature);
        param.put("nonce", nonce);
        if (null != options.getUid()) {
            param.put("uid", options.getUid());
        }
        String[] tags = options.getTags();

        final String PREFIX = "--";
        final String END = "\r\n";
        final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String result = null;

        URL url = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(options.getConnectTimeout());
        conn.setReadTimeout(options.getReadTimeout());
        conn.setDoInput(true); // 允许输入流
        conn.setDoOutput(true); // 允许输出流
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST"); // 请求方式
        conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

        /**
         * 当文件不为空，把文件包装并且上传
         */
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        StringBuffer stringBuffer = null;
        String params = "";

        /***
         * 以下是用于上传参数
         */
        if (param != null && param.size() > 0) {
            Iterator<String> it = param.keySet().iterator();
            while (it.hasNext()) {
                stringBuffer = null;
                stringBuffer = new StringBuffer();
                String key = it.next();
                String value = param.get(key);
                stringBuffer.append(PREFIX).append(BOUNDARY).append(END);
                stringBuffer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(END)
                        .append(END);
                stringBuffer.append(value).append(END);
                params = stringBuffer.toString();
                dos.write(params.getBytes("UTF-8"));
            }
            joinValues(tags, "tag", BOUNDARY, params, dos);
        }
        for (int i = 0; i < fileLists.size(); i++) {

            String uploadFile = fileLists.get(i);
            dos.write((PREFIX + BOUNDARY + END).getBytes("UTF-8"));
            dos.write(("Content-Disposition:form-data; name=\"" + "image" + "\"; filename=\"" + fileLists.get(i) + "\""
                    + END).getBytes("UTF-8"));

            dos.write(END.getBytes("UTF-8"));
            FileInputStream fStream = new FileInputStream(uploadFile);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }
            dos.writeBytes(END);
            fStream.close();
        }

        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + END).getBytes("UTF-8");
        dos.write(end_data);
        dos.flush();
        int res = conn.getResponseCode();
        if (res == 200) {
            InputStream input = conn.getInputStream();
            StringBuffer sb1 = new StringBuffer();
            int ss;
            while ((ss = input.read()) != -1) {
                sb1.append((char) ss);
            }

            result = sb1.toString();
        }
        return new ClassificationResult(res, result);

    }

    /**
     * @param values
     * @param valueName
     * @param BOUNDARY
     * @param params
     * @param dos       拼接数组参数
     */
    public static void joinValues(String[] values, String valueName, String BOUNDARY, String params,
                                  DataOutputStream dos) {
        final String PREFIX = "--";
        final String END = "\r\n";
        if (values != null && values.length > 0) {
            StringBuffer stringBuffer = null;
            for (int i = 0; i < values.length; i++) {
                stringBuffer = null;
                stringBuffer = new StringBuffer();
                String value = values[i];
                stringBuffer.append(PREFIX).append(BOUNDARY).append(END);
                stringBuffer.append("Content-Disposition: form-data; name=\"").append(valueName).append("\"")
                        .append(END).append(END);
                stringBuffer.append(value).append(END);
                params = stringBuffer.toString();
                try {
                    dos.write(params.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * URL 方式测试文件
     *
     * @param actionUrl 请求路径
     * @param timestamp 时间戳
     * @param nonce
     * @param signature 鉴权信息
     * @param fileLists 文件url 列表
     * @return
     */
    public static ClassificationResult uploadUri(String actionUrl, String timestamp, String nonce, String signature,
                                                 ArrayList<String> fileLists, Options options) throws Exception {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        String httpArg = "timestamp=" + timestamp + "&nonce=" + nonce + "&signature="
                + URLEncoder.encode(signature, java.nio.charset.StandardCharsets.UTF_8.toString());

        String[] tags = options.getTags();
        if (tags != null && tags.length > 0) {
            for (int i = 0; i < tags.length; i++) {
                httpArg += "&tag=" + tags[i];
            }
        }
        if (fileLists.size() > 0) {
            for (int i = 0; i < fileLists.size(); i++) {
                httpArg += "&image="
                        + URLEncoder.encode(fileLists.get(i), java.nio.charset.StandardCharsets.UTF_8.toString());
            }
        }

        URL connect_url = new URL(actionUrl);
        HttpURLConnection connection = (HttpURLConnection) connect_url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        connection.setConnectTimeout(options.getConnectTimeout());
        connection.setReadTimeout(options.getReadTimeout());
        connection.setDoOutput(true);
        connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
        connection.connect();
        InputStream is = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String strRead = null;
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        return new ClassificationResult(connection.getResponseCode(), sbf.toString());
    }


    /**
     * URL 方式测试文件
     *
     * @param actionUrl 请求路径
     * @param timestamp 时间戳
     * @param nonce
     * @param signature 鉴权信息
     * @param fileLists 文件url 列表
     * @return
     */
    public static ClassificationResult uploadSpeechUri(String actionUrl, long timestamp, double nonce, String signature,
                                                       ArrayList<SpeechStream> fileLists, Options options) throws Exception {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        JSONObject requestJson = new JSONObject();
        JSONArray speechStream = new JSONArray();
        if (fileLists.size() > 0) {
            for (SpeechStream speech : fileLists) {
                JSONObject speechJson = new JSONObject();
                speechJson.put("url", speech.getUrl());
                if (speech.getCallbackUrl() != null && speech.getCallbackUrl() != "") {
                    speechJson.put("callback", speech.getCallbackUrl());
                }
                if (speech.getRoomId() != null && speech.getRoomId() != "") {
                    speechJson.put("roomId", speech.getRoomId());
                }
                if (speech.getUserId() != null && speech.getUserId() != "") {
                    speechJson.put("userId", speech.getUserId());
                }
                if (speech.getFormId() != null && speech.getFormId() != "") {
                    speechJson.put("forumId", speech.getFormId());
                }
                speechJson.put("callbackRules", speech.getCallbackRules());
                speechJson.put("returnPreSpeech", speech.isReturnPreSpeech());
                speechStream.add(speechJson);
            }
        }
        requestJson.put("speechStream", speechStream);
        requestJson.put("nonce", nonce);
        requestJson.put("signature", signature);
        requestJson.put("timestamp", timestamp);

        URL connect_url = new URL(actionUrl);
        HttpURLConnection connection = (HttpURLConnection) connect_url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setConnectTimeout(options.getConnectTimeout());
        connection.setReadTimeout(options.getReadTimeout());
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.getOutputStream().write(requestJson.toString().getBytes("UTF-8"));
        connection.connect();
        InputStream is = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String strRead = null;
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        return new ClassificationResult(connection.getResponseCode(), sbf.toString());
    }

    /**
     * 关闭语音流
     */
    public static ClassificationResult closeSpeechStream(String actionUrl, long timestamp, double nonce, String signature,
                                                         ArrayList<String> requestIdLists, Options options) throws Exception {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        JSONObject requestJson = new JSONObject();
        JSONArray speechStream = new JSONArray();
        if (requestIdLists.size() > 0) {
            for (String requestId : requestIdLists) {
                JSONObject speechJson = new JSONObject();
                speechJson.put("requestId", requestId);
                speechStream.add(speechJson);
            }
        }
        requestJson.put("speechStream", speechStream);
        requestJson.put("nonce", nonce);
        requestJson.put("signature", signature);
        requestJson.put("timestamp", timestamp);

        URL connect_url = new URL(actionUrl);
        HttpURLConnection connection = (HttpURLConnection) connect_url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setConnectTimeout(options.getConnectTimeout());
        connection.setReadTimeout(options.getReadTimeout());
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.getOutputStream().write(requestJson.toString().getBytes("UTF-8"));
        connection.connect();
        InputStream is = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String strRead = null;
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        return new ClassificationResult(connection.getResponseCode(), sbf.toString());
    }


    /**
     * URL 方式测试文件
     *
     * @param actionUrl 请求路径
     * @param timestamp 时间戳
     * @param nonce
     * @param signature 鉴权信息
     * @return
     */
    public static ClassificationResult uploadVideo(String actionUrl, String timestamp, String nonce, String signature, Video file, Options options) throws Exception {
        String BOUNDARY = UUID.randomUUID().toString();
        Map<String, String> param = new HashMap<String, String>();
        param.put("timestamp", timestamp);
        param.put("signature", signature);
        param.put("nonce", nonce);
        param.put("interval", String.valueOf(file.getInterval()));
        param.put("maxFrames", String.valueOf(file.getMaxFrames()));
        if (file.getVideo() != null && file.getVideo().startsWith("http")) {
        param.put("video", String.valueOf(file.getVideo()));
        }
        if (null != options.getUid()) {
            param.put("uid", options.getUid());
        }
        String[] tags = options.getTags();

        final String PREFIX = "--";
        final String END = "\r\n";
        final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String result = null;

        URL url = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(options.getConnectTimeout());
        conn.setReadTimeout(options.getReadTimeout());
        conn.setDoInput(true); // 允许输入流
        conn.setDoOutput(true); // 允许输出流
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST"); // 请求方式
        conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

        /**
         * 当文件不为空，把文件包装并且上传
         */
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        StringBuffer stringBuffer = null;
        String params = "";

        /***
         * 以下是用于上传参数
         */
        if (param != null && param.size() > 0) {
            Iterator<String> it = param.keySet().iterator();
            while (it.hasNext()) {
                stringBuffer = null;
                stringBuffer = new StringBuffer();
                String key = it.next();
                String value = param.get(key);
                stringBuffer.append(PREFIX).append(BOUNDARY).append(END);
                stringBuffer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(END)
                        .append(END);
                stringBuffer.append(value).append(END);
                params = stringBuffer.toString();
                dos.write(params.getBytes("UTF-8"));
            }
            joinValues(tags, "tag", BOUNDARY, params, dos);
        }

        if (file.getVideo() != null && !file.getVideo().startsWith("http")) {
            dos.write((PREFIX + BOUNDARY + END).getBytes("UTF-8"));
            dos.write(("Content-Disposition:form-data; name=\"" + "video" + "\"; filename=\"" + file.getVideo() + "\""
                    + END + "Content-Type: image/gif" + END).getBytes("UTF-8"));
            dos.writeBytes(END);
            FileInputStream fStream = new FileInputStream(file.getVideo());
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }
            dos.writeBytes(END);
            fStream.close();
        }

        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + END).getBytes("UTF-8");
        dos.write(end_data);
        dos.flush();
        int res = conn.getResponseCode();
        if (res == 200) {
            InputStream input = conn.getInputStream();
            StringBuffer sb1 = new StringBuffer();
            int ss;
            while ((ss = input.read()) != -1) {
                sb1.append((char) ss);
            }

            result = sb1.toString();
        }
        return new ClassificationResult(res, result);
    }

}

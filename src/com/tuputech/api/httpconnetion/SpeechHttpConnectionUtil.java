package com.tuputech.api.httpconnetion;

import com.tuputech.api.model.*;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by soap on 16/7/4. 上传文件并返回结果
 */

public class SpeechHttpConnectionUtil {


    /**
     * 测试视频信息
     *
     * @param actionUrl 请求路径
     * @param timestamp 时间戳
     * @param nonce
     * @param signature 鉴权信息
     * @return
     */
    public static ClassificationResult uploadSpeechAsync(String actionUrl, String timestamp, String nonce, String signature, RecordingFile file) throws Exception {

        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        JSONObject requestJson = new JSONObject();
        JSONObject recordingFile = new JSONObject();

        recordingFile.put("url", file.getUrl());
        if (file.getCustomInfo() != null) {
            recordingFile.put("customInfo", file.getCustomInfo());
        }

        if (file.getCallbackUrl() != null) {
            recordingFile.put("callbackUrl", file.getCallbackUrl());
        }
        if (file.getCallbackRule() != null) {
            recordingFile.put("callbackRule", file.getCallbackRule());
        }
        if (file.getRoomId() != null) {
            recordingFile.put("roomId", file.getRoomId());
        }
        if (file.getUserId() != null) {
            recordingFile.put("userId", file.getUserId());
        }
        if (file.getForumId() != null) {
            recordingFile.put("forumId", file.getForumId());
        }

        requestJson.put("recording", recordingFile);
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
    public static ClassificationResult uploadSpeechSync(String actionUrl, String timestamp, String nonce, String signature, String[] file, String taskId, String speechType) throws Exception {
        String BOUNDARY = UUID.randomUUID().toString();
        Map<String, String> param = new HashMap<String, String>();
        param.put("timestamp", timestamp);
        param.put("signature", signature);
        param.put("nonce", nonce);
        if (speechType == "url") {
            for (int i = 0; i < file.length; i++) {
                param.put("speech", String.valueOf(file[i]));

            }
        }
        if (null != taskId && !taskId.isEmpty()) {
            param.put("task", taskId);

        }

        final String PREFIX = "--";
        final String END = "\r\n";
        final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String result = null;

        URL url = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
        }


        if (speechType == "file") {
            for (int i = 0; i < file.length ; i++) {
                dos.write((PREFIX + BOUNDARY + END).getBytes("UTF-8"));
                dos.write(("Content-Disposition:form-data; name=\"" + "speech" + "\"; filename=\"" + file[i] + "\""
                        + END + "Content-Type: multipart/form-data" + END).getBytes("UTF-8"));
                dos.writeBytes(END);
                FileInputStream fStream = new FileInputStream(file[i]);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = fStream.read(buffer)) != -1) {
                    dos.write(buffer, 0, length);
                }
                dos.writeBytes(END);
                fStream.close();
            }

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

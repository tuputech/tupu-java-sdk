package com.tuputech.api.util;

/**
 * Created by soap on 16/7/4. 配置信息
 */
public class ConfigUtil {

    /**
     * 测试数据类型
     */
    public final static class UPLOAD_TYPE {
        /**
         * upload file
         **/
        public final static String UPLOAD_IMAGE_TYPE = "image";
        /**
         * upload uri
         */
        public final static String UPLOAD_URI_TYPE = "url";
    }

    /**
     * api 调用地址
     */
    public final static class NET_WORK {

        public final static String API_URI = "http://api.open.tuputech.com/v3/recognition/";

        //语音识别流接口
        public final static String SPEECH_API_URI = "http://api.open.tuputech.com/v3/recognition/speech/stream/";

        //语音识别流关闭接口
        public final static String SPEECH_API_URI_CLOSE = "http://api.open.tuputech.com/v3/recognition/speech/stream/close/";

    }

    /**
     * 文件限制
     */

    public final static class FILE_LIMIT {
        /**
         * 图片最大 800KB 左右
         */
        public final static int MAX_IMAGE_LENGTH = 800 * 1024;

        /**
         * 每个请求最大图片数量
         */
        public final static int MAX_IMAGE_LIST_SIZE = 200;

    }

    /**
     * 公用Key文件名
     */
    public final static String PUBLIC_TUPU_KEY_PATH = "open_tuputech_com_public_key.pem";

}

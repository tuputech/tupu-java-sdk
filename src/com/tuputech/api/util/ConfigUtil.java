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
     * VIDEO测试数据类型
     */
    public final static class VIDEO_UPLOAD_TYPE {
        /**
         * upload file
         **/
        public final static String UPLOAD_VIDEO_FILE_TYPE = "file";
        /**
         * upload uri
         */
        public final static String UPLOAD_VIDEO_STREAM_TYPE = "stream";
    }

    /**
     * api 调用地址
     */
    public final static class NET_WORK {

        public final static String API_URI = "http://api.open.tuputech.com/v3/recognition/";

        //人脸实名认证
        public final static String FACE_API_URI = "http://api.open.tuputech.com/v3/recognition/face/verify/";

        //语音识别流接口
        public final static String SPEECH_API_URI = "http://api.open.tuputech.com/v3/recognition/speech/stream/";

        //语音识别流关闭接口
        public final static String SPEECH_API_URI_CLOSE = "http://api.open.tuputech.com/v3/recognition/speech/stream/close/";

        //视频接口
        public final static String SYNC_VIDEO_API_URI = "http://api.open.tuputech.com/v3/recognition/video/syncscan/";

        //文本识别接口
        public final static String TEXT_API_URI = "http://api.open.tuputech.com/v3/recognition/text/";

        //异步视频文件识别
        public final static String VIDEO_ASYNC_FILE_API_URI = "http://api.open.tuputech.com/v3/recognition/video/asyncscan/";

        //异步视频文件流识别
        public final static String VIDEO_ASYNC_STREAM_API_URI = "http://api.open.tuputech.com/v3/recognition/video/stream/";

        //Base64图片
        public final static String IMAGE_BASE64_URL = "http://api.open.tuputech.com/v3/recognition/image/sync/base64/";

        //同步语音识别接口
        public final static String SPEECH_SYNC_API_URI = "http://api.speech.tuputech.com/v3/recognition/speech/";

        //同步语音识别接口
        public final static String SPEECH_ASYNC_API_URI = "http://api.speech.tuputech.com/v3/recognition/speech/recording/async/";

        //获取异步视频结果
        public final static String VIDEO_ASYNC_RESULT_API_URI = "http://api.video.tuputech.com/v3/recognition/video/result/";

        public final static String VIDEO_ASYNC_RESULT_API_ABROAD_URI = "http://api-us.open.tuputech.com/v3/recognition/video/result/";

        //获取异步语音结果
        public final static String SPEECH_ASYNC_RESULT_API_URI = "http://api.speech.tuputech.com/v3/recognition/speech/recording/result/";

//        public final static String SPEECH_ASYNC_RESULT_API_ABROAD_URI = "http://api.speech.tuputech.com/v3/recognition/speech/recording/async/";

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

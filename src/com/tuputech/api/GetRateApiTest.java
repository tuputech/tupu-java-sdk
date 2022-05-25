package com.tuputech.api;

import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

/**
 * 异步视频获取结果API
 */
public class GetRateApiTest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url 区分国内国外 国外使用 ConfigUtil.NET_WORK.RATE_OVERSEA_API
        //查询异步使用地址为 ConfigUtil.NET_WORK.RATE_ASYNC_API
        String rateUrl = ConfigUtil.NET_WORK.RATE_API;



        GetRateApi api = new GetRateApi(secretId, privateKey, rateUrl);
        //获取异步语音结果
        JSONObject result = api.getRate();
        System.out.println("do speech result is : " + result);

    }
}

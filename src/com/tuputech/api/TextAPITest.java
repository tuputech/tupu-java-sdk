package com.tuputech.api;
import com.tuputech.api.model.Text;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;


public class TextAPITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path 以 .pem 结尾的话，当做密钥文件，其它字符串当做密钥内容处理
        String privateKey = "";
        // request Url
        String textApiUrl = ConfigUtil.NET_WORK.TEXT_API_URI;

        //单次最大可传10个
        ArrayList<Text> textArrayList = new ArrayList<>(10);

        Text text = new Text();

        //带检测的文本内容，可以为词语或句子，必传
        text.setContent("أمك ليس لديها مؤخرة\n");


        //客户自定义信息，方便根据该id找到相关的文本,建议可设置为secretId + 当前时间 + 随机数，参考请求示例 可虚线
        text.setContentId("");
        //用户Id 可选
        text.setUserId("");
        //板块Id 可选
        text.setForumId("");

        textArrayList.add(text);


        TextApi api = new TextApi(secretId, privateKey, textApiUrl);
        //执行文本检测
        JSONObject result = api.doTextApi(textArrayList);
        System.out.println("do text result is : " + result);


    }
}

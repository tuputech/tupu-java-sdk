package com.tuputech.api;

import com.tuputech.api.model.Options;
import com.tuputech.api.model.Text;
import com.tuputech.api.model.Video;
import com.tuputech.api.util.ConfigUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;


public class TextAPITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path
        String privateKey = "";
        // request Url
        String videoApiUrl = ConfigUtil.NET_WORK.TEXT_API_URI;

        //单次最大可传10个
        ArrayList<Text> textArrayList = new ArrayList<>(10);

        Text text = new Text();
        Text text2 = new Text();

        //带检测的文本内容，可以为词语或句子，必传
        text.setContent("东方红，太阳升，中国出来个毛泽东");
        text2.setContent("中国出来个毛泽东");

        //客户自定义信息，方便根据该id找到相关的文本,建议可设置为secretId + 当前时间 + 随机数，参考请求示例 可虚线
        text.setContentId("");
        //用户Id 可选
        text.setUserId("");
        //板块Id 可选
        text.setForumId("");

        textArrayList.add(text);
        textArrayList.add(text2);



        TextApi api = new TextApi(secretId, privateKey, videoApiUrl);
        //执行同步视频检测
        JSONObject result = api.doTextApi(textArrayList);
        System.out.println("do text result is : "+ result);


    }
}

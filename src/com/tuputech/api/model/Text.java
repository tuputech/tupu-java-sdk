package com.tuputech.api.model;

/**
 * @author soap
 * @date 2021-03-25
 * 文本识别 Options
 */
public class Text {
    //带检测的文本内容，可以为词语或句子
    private String content;
    //客户自定义信息，方便根据该id找到相关的文本,建议可设置为secretId + 当前时间 + 随机数，参考请求示例
    private String contentId;
    //用户Id
    private String userId;
    //板块Id
    private String forumId;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }
}

<a href="https://www.tuputech.com/">
    <img src="https://www.tuputech.com/zh/images/brandpage/tuputech_logo_horizontal_black.png" alt="图普科技"
         title="图普科技" align="right" />
</a>
# Tuputech SDK For Java
图普科技-图像识别云服务 Java API https://www.tuputech.com/

#使用方法

###账户申请,证书下载

- 请联系图普售前/客户支持，明确需求，以便申请账户、申请开通 secretId；

###目录介绍

```
examples/
		libs/tupuApi.jar   //图普 API SDK ,已集成json 解析 jar
        src/com/soap/api/APITest.java  //调用示例

libs/					 //json 解析jar
	commons-beanutils-1.8.0.jar
	commons-collections-3.2.1.jar
	commons-lang-2.5.jar
	commons-logging-1.2.jar
	ezmorph-1.0.6.jar
	json-lib-2.4-jdk15.jar

src/
	com/tuputech/api				     // SDK 源码
	open_tuputech_com_public_key.pem	//图普公钥,验证返回结果
```

###API 调用示例

```java
public class APITest {

    public static void main(String[] args) {
        // secret id
        String secretId = "";
        // private KEY path
        String privateKey = "";
        // request Url
        String requestUrl = "http://api.open.tuputech.com/v3/recognition/";
        // fileList imageFile or url
        ArrayList<String> fileLists = new ArrayList<String>();
        // tags
        String tags[] = { "tag1", "tag2" };
        fileLists.add("../test1.jpg");
        fileLists.add("../test2.jpg");
        // options
        Options options = new Options();
        options.setTags(tags);

        // http timeout config, unit: ms, default 15 seconds
        options.setConnectTimeout(16000);
        options.setReadTimeout(16000);

        // options.setUid("your_uid"); // 第三方客户标识

        /**
         * @param secretId   用户secretId
         * @param privateKey 用户私钥，如果传递以 .pem结尾的话，当做密钥文件，其它字符串当做密钥内容处理
         * @param requestUrl            请求接口地址
         */
        Api api = new Api(secretId, privateKey, requestUrl);

        /**
         * @param fileType  传入的数据类型，ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE为本地文件
         *                  ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE 为图片 Url
         * @param fileLists 文件集合(本地文件路径或者 Url)
         * @param options   可选参数 tags:
         *                  用于给图片附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）。方便后续根据tag搜索到相关的图片 uid:
         *                  作为第三方客户标识
         * @return
         */
        JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileLists, options);
    }
}
```

#API 说明
###construct a TUPU API instance

```java
Api api = new Api(secretId, privateKey, requestUrl);
```

- `secretId`Type:String,your secretId, contact us to apply your own secretId
- `privateKeyPath` Type:String, /path/to/your/private/key.pem
- `requestUrl` Type:String, default:"http://api.open.tuputech.com/v3/recognition/"

```java
JSONObject result = api.doApiTest(fileType,fileList,options)
```

- `fileType` Type:String, ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE or ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE
- `fileList` Type:ArrayList<String>,images path or urls list
- `options` Type:Object, optional param.
  tags: [ 'tag1', 'tag2' ] tag of image (optional); string value as general tag for all files; if count of tags is less than count of files, the last tag will be used for the rest.
  uid: as third party customer identification.

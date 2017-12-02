package com.tuputech.api;

import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.ArrayList;

import com.tuputech.api.httpconnetion.HttpConnectionUtil;
import com.tuputech.api.model.ClassificationResult;
import com.tuputech.api.model.Options;
import com.tuputech.api.util.ConfigUtil;
import com.tuputech.api.util.ErrorUtil;
import com.tuputech.api.util.SignatureAndVerifyUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author soap API
 */


public class Api {
	private String secretId;
	private String url;
	private PrivateKey privateKey;

	/**
	 * @param secretId
	 *            用户secretId
	 * @param pkPath
	 *            用户私钥
	 */
	public Api(String secretId, String pkPath, String requestUrl) {
		if (null == requestUrl) {
			requestUrl = "http://api.open.tuputech.com/v3/recognition/";
		}
		this.secretId = secretId;
		this.url = requestUrl + secretId;
		this.privateKey = SignatureAndVerifyUtil.readPrivateKey(pkPath);

	}

	/**
	 * 
	 * @param fileType
	 *            传入的数据类型，ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE为本地文件
	 *            ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE 为图片 Url
	 * @param fileLists
	 *            文件集合
	 * @param options
	 *            [可选] 
	 *            tags: 用于给图片附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）。方便后续根据tag搜索到相关的图片
	 *            uid: 作为第三方客户标识
	 * 
	 * @return
	 */
	public JSONObject doApiTest(String fileType, ArrayList<String> fileLists, Options options) {
		if (fileLists == null || fileLists.isEmpty()) {
			return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_NO_FILE, "");
		}
		String timestamp = Math.round(System.currentTimeMillis() / 1000.0) + "";
		String nonce = Math.random() + "";
		String sign_string = secretId + "," + timestamp + "," + nonce;

		String signature = SignatureAndVerifyUtil.Signature(privateKey, sign_string);

		ClassificationResult classificationResult = null;

		long startTime = System.currentTimeMillis();
		long endTime = 0;
		float time = 0;
		try {
			// 得到签名
			if (fileType == ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE) {
				classificationResult = HttpConnectionUtil.uploadImage(url, secretId, timestamp, nonce, signature,
						fileLists, options);
			} else if (fileType == ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE) {
				classificationResult = HttpConnectionUtil.uploadUri(url, timestamp, nonce, signature, fileLists,
						options);
			}
			
			if (classificationResult.getResultCode() == 200) {
				String result = classificationResult.getResult();
				// 判断当前字符串的编码格式
				if (result.equals(new String(result.getBytes("iso8859-1"), "iso8859-1"))) {
					result = new String(result.getBytes("iso8859-1"), "utf-8");
				}
				JSONObject jsonObject = JSONObject.fromObject(result);

				String result_json = jsonObject.getString("json");
				String code = JSONObject.fromObject(result_json).getString("code");
				if (Integer.valueOf(code) == 0) {
					String result_signature = jsonObject.getString("signature");
					// 进行验证
					boolean verify = SignatureAndVerifyUtil.Verify(result_signature, result_json);
					endTime = System.currentTimeMillis();
					time = (float) (endTime - startTime) / (float) 1000;
					if (verify) {
						System.out.println("TUPU API: response verify succeed, total time" + time + "s");
						return JSONObject.fromObject(result_json);
					} else {
						System.out.println("TUPU API: response verify failed, total time" + time + "s");
						return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_RESULT_VERIFY_FAILED, "");
					}
				} else {
					System.out.println("TUPU API: response verify failed, total time" + time + "s");
					return ErrorUtil.getErrorMsg(Integer.valueOf(code), "");
				}
			} else {
				System.out.println("TUPU API: response verify failed, total time" + time + "s");
				return ErrorUtil.getErrorMsg(classificationResult.getResultCode(), "");
			}
		} catch (Exception e) {
			System.out.println("TUPU API: response verify failed, total time" + time + "s");
			System.out.println("TUPU API: response verify failed, error is " + e.getMessage());
			return ErrorUtil.getErrorMsg(ErrorUtil.ERROR_CODE_OTHERS, e.getMessage());
		}
	}
}
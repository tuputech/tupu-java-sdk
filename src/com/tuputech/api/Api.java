package com.tuputech.api;

import java.util.ArrayList;

import com.tuputech.api.httpconnetion.HttpConnectionUtil;
import com.tuputech.api.util.ConfigUtil;
import com.tuputech.api.util.SignatureAndVerifyUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author soap API
 */
public class Api {
	private String secretId;
	private String signature;
	private String timestamp;
	private String nonce;
	private String sign_string;
	

	/**
	 * @param secretId
	 *            用户secretId
	 * @param pkPath
	 *            用户私钥
	 */
	public Api(String secretId, String pkPath) {
		this.secretId = secretId;
		timestamp = Math.round(System.currentTimeMillis() / 1000.0) + "";
		nonce = Math.random() + "";
		sign_string = secretId + "," + timestamp + "," + nonce;
		signature = SignatureAndVerifyUtil.Signature(pkPath, sign_string);

	}

	/**
	 * 
	 * @param fileType
	 *            传入的数据类型，ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE为本地文件
	 *            ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE 为图片 Url
	 * @param tags
	 * 		  用于给图片附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）。方便后续根据tag搜索到相关的图片
	 * @param fileLists
	 *            文件集合
	 * 
	 * @return
	 */
	public String doApiTest(String fileType, String tags[], ArrayList<String> fileLists) {
		
		String url = ConfigUtil.NET_WORK.API_URI + secretId;
		

		if (fileLists == null || fileLists.isEmpty()) {
			return "文件列表为空";
		}
		if (fileLists.size() > ConfigUtil.FILE_LIMIT.MAX_IMAGE_LIST_SIZE) {
			return "单次传入文件大小为" + ConfigUtil.FILE_LIMIT.MAX_IMAGE_LIST_SIZE;
		}

		String result = null;
		// 得到签名 
		if (fileLists != null && !fileLists.isEmpty() && fileType == ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE) {
			result = HttpConnectionUtil.uploadImage(url, secretId, timestamp, nonce, signature, fileLists, tags);
		} else if (fileLists != null && !fileLists.isEmpty() && fileType == ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE) {
			result = HttpConnectionUtil.uploadUri(url, timestamp, nonce, signature, fileLists, tags);

		}
		if (result != null && !result.equals("err")) {
			JSONObject jsonObject = JSONObject.fromObject(result);
			String result_json = jsonObject.getString("json");
			String result_signature = jsonObject.getString("signature");
			// 进行验证
			boolean verify = SignatureAndVerifyUtil.Verify(result_signature, result_json);
			if (verify) {
				result = result_json;
				System.out.println("验证成功：");
			} else {
				System.out.println("验证失败：");
			}
		} else if (result == null) {
			return "传入文件为空";
		}
		return result;
	}

}
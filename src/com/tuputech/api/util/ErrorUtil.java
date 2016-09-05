package com.tuputech.api.util;

import net.sf.json.JSONObject;

public class ErrorUtil {
	// 0：调用成功;
	public final static int CODE_SUCCESS = 0;
	// 1：授权失败；
	public final static int ERROR_CODE_AUTHENTICATION = 1;
	// 2：模型ID错误
	public final static int ERROR_CODE_MODEL_ID_ERROR = 2;
	// 没有文件
	public final static int ERROR_CODE_NO_FILE = 3;
	// 4：API版本号错误
	public final static int ERROR_CODE_API_VISION_ERROR = 4;
	// 5：API版本已弃用；
	public final static int ERROR_CODE_API_DEPRECATED = 5;
	// 6：secretId 错误；
	public final static int ERROR_CODE_SECRET_ID_ERROR = 6;
	// 7：任务Id错误，您的secretId不能调用该任务；
	public final static int ERROR_CODE_TASK_ID_ERROR = 7;
	// 8：secretId状态异常；
	public final static int ERROR_CODE_SECRET_ID_STATUS_ERROR = 8;
	// 9：尚未上传证书；
	public final static int ERROR_CODE_NO_CERTIFICATE = 9;
	// 10：pipeId 错误；
	public final static int ERROR_CODE_PIPE_ID_ERROR = 10;
	// 11：没有callback参数；
	public final static int ERROR_CODE_CALLBACK_ERROR = 11;
	// 12：时间参数错误；
	public final static int ERROR_CODE_TIME_ERROR = 12;
	// 13：没有配置计算任务或者都已关闭；
	public final static int ERROR_CODE_TASK_CLOSED = 13;
	// 14：图片错误：
	public final static int ERROR_CODE_IMAGE_ERROR = 14;
	// 404、400、下载失败、过大等；
	public final static int ERROR_CODE_NOT_FOUND = 404;
	// 404、400、下载失败、过大等；
	public final static int ERROR_CODE_DOWNLOAD_FAILED = 400;
	// 100：服务器错误；
	public final static int ERROR_CODE_SEVER_ERROR = 100;
	// 101：未知错误。
	public final static int ERROR_CODE_UNKNOWN_ERROR = 101;
	//102:结果验证失败
	public final static int ERROR_CODE_RESULT_VERIFY_FAILED = 102;
	public static JSONObject getErrorMsg(int errCode, String errMsg) {
		JSONObject obj = new JSONObject();
		switch (errCode) {
		case CODE_SUCCESS:
			errMsg ="success";
			break;
		case ERROR_CODE_AUTHENTICATION:
			errMsg = "authentication failed ";
			break;
		case ERROR_CODE_MODEL_ID_ERROR:
			errMsg = "model id error";
			break;
		case ERROR_CODE_NO_FILE:
			errMsg ="no file to upload";
			break;
		case ERROR_CODE_API_VISION_ERROR:
			errMsg = "api version error";
			break;
		case ERROR_CODE_API_DEPRECATED:
			errMsg = "api version deprecated";
			break;
		case ERROR_CODE_SECRET_ID_ERROR:
			errMsg = "secretId id error";
			break;
		case ERROR_CODE_TASK_ID_ERROR:
			errMsg = "task id error";
			break;
		case ERROR_CODE_SECRET_ID_STATUS_ERROR:
			errMsg = "secretId  status error";
			break;
		case ERROR_CODE_NO_CERTIFICATE:
			errMsg = "no cetificate upload";
			break;
		case ERROR_CODE_PIPE_ID_ERROR:
			errMsg ="pipe id error";
			break;
		case ERROR_CODE_CALLBACK_ERROR:
			errMsg = "callback error";
			break;
		case ERROR_CODE_TIME_ERROR:
			errMsg  ="parameter time error";
			break;
		case ERROR_CODE_TASK_CLOSED:
			errMsg  ="task has closed";
			break;
		case ERROR_CODE_IMAGE_ERROR:
			errMsg = "files formats error";
			break;
		case ERROR_CODE_NOT_FOUND:
			errMsg = "files not found";
			break;
		case ERROR_CODE_DOWNLOAD_FAILED:
			errMsg = "files download failed";
			break;
		case ERROR_CODE_SEVER_ERROR:
			errMsg = "server has a problem";
			break;
		case ERROR_CODE_UNKNOWN_ERROR:
			errMsg ="server has a problem";	
			break;
		case ERROR_CODE_RESULT_VERIFY_FAILED:
			errMsg = "response verify failed";
			break;
		default:
			if (null == errMsg ||errMsg == "") {
				errMsg ="unknown error";
			}
			break;
		}
		obj.put("code", errCode);
		obj.put("message", errMsg);
		return obj;
	}
}

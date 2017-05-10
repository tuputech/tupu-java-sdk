package com.tuputech.api.model;

public class ClassificationResult {
	
	private int resultCode = 0;
	
	private String result = null;

	public ClassificationResult(int resultCode,String result) {
		this.resultCode = resultCode;
		this.result = result;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	

}

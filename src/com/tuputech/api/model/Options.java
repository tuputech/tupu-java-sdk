package com.tuputech.api.model;public class Options {	private String[] tags;	private String CID = null;	private String uid = null;	public Options() {	}	public Options(String[] tags, String uid, String CID) {		this.tags = tags;		this.uid = uid;		this.CID = CID;	}	public String[] getTags() {		return tags;	}	public void setTags(String[] tags) {		this.tags = tags;	}	public String getUid() {		return uid;	}	public void setUid(String uid) {		this.uid = uid;	}	public String getCID() {		return CID;	}	public void setCID(String CID) {		this.CID = CID;	}}
package com.tuputech.api;

import java.util.regex.Pattern;

public class UriTest {

	public static void main(String[] args) {

		/* ***********************
		 * Pattern.CASE_INSENSITIVE 不区分大小写
		 * 仅仅以http://打头，不支持携带查询字符串
		 * [\\w-\\.] 限制域名和路径仅仅由a-z0-9_-.这么几个字符
		 * (?:/|(?:/[\\w\\.\\-]+)*(?:/[\\w\\.\\-]+\\.do))? 这个表示路径可以为空、/、和.do结尾
		 *************************/
		Pattern exp=Pattern.compile("^http://[\\w-\\.]+(?:/|(?:/[\\w\\.\\-]+)*(?:/[\\w\\.\\-]+\\.do))?$", Pattern.CASE_INSENSITIVE);
		
		System.out.println(exp.matcher("http://202.199.160.62/validateCodeAction.do").matches());//true
		System.out.println(exp.matcher("http://202.199.160.62").matches());//true
		System.out.println(exp.matcher("http://202.199.160.62/folder/validateCodeAction.do").matches());//true
		System.out.println(exp.matcher("http://www.baidu.com").matches());//true
		System.out.println(exp.matcher("http://www.baidu.com/").matches());//true
		System.out.println(exp.matcher("http://localhost/").matches());//true
		System.out.println(exp.matcher("http://localhost/vv.do").matches());//true
		
		System.out.println("image".equals("url"));//false

		
	}
}

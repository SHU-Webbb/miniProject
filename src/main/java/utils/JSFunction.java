package utils;

import jakarta.servlet.jsp.JspWriter;

public class JSFunction {
	
	/*
	 * 알림차을 띄운 후 지정한 url로 이동하는 메소드
	 * msg - 알림창에 표시할 메시지
	 * url - 알림창에서 이동할 url
	 * out - 자바 스크립트 코드를 출력할 out 내장객체
	 */
	
	public static void alertLocation(String msg, String url, JspWriter out) {
		
		try {
			String script = "<script>"
						+"	alert('" + msg + "');"
						+"	location.href='" + url + "';"
						+" </script>";
			out.println(script);  //사용자의 브라우저 화면에 javascript 코드 출력
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 알림창을 띄운 후, 이전 페이지로 돌아가는 메소드
	 * 
	 */
public static void alertBack(String msg, JspWriter out) {
		
		try {
			String script = "<script>"
						+"	 alert('" + msg + "');"
						+"	 history.go(-1);"
						+" </script>";
			out.println(script);  //사용자의 브라우저 화면에 javascript 코드 출력
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}






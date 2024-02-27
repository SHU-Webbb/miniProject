<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="webtoonplatform.dao.loginDAO" %>    
<%@ page import="webtoonplatform.dto.loginDTO" %>    
<%
	String userId = request.getParameter("id");
	String userPwd = request.getParameter("password");
	
	
    String driver = application.getInitParameter("OracleDriver");
	String url = application.getInitParameter("OracleURL");
	String uid = application.getInitParameter("OracleId");
	String pwd = application.getInitParameter("OraclePwd");

	loginDAO dao = new loginDAO(application);
	loginDTO friend = dao.getloginDTO(userId, userPwd);
	
	System.out.println("friend=" + friend);

	System.out.println("userId=" + userId);
	System.out.println("userPwd=" + userPwd);
	
	if (friend.getId() != null) { // 로그인 성공
		// 세션에 사용자 정보 저장
		
		session.setAttribute("id", friend.getId());
		session.setAttribute("password", friend.getPassword());
		session.setAttribute("usernum", friend.getUsernum());
		System.out.println(session.getAttribute("usernum"));
		response.sendRedirect(request.getContextPath() + "/Post_Write_Edit/MainFrom.jsp");
		
	
	    } else {	//로그인 실패
		request.setAttribute("LoginErrMsg", "로그인 되지 않았습니다");
		request.getRequestDispatcher("loginForm.jsp").forward(request, response);
		
	}
%>


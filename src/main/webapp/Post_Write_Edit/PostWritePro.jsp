<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDTO"%>
<%@ page import="post.PostDAO"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.nio.file.Paths"%>
<%@ page import="java.nio.file.StandardCopyOption"%>
<%@ page import="java.util.*"%>

<%
// 이미지와 썸네일 Part 객체 가져오기
Part imageUrlPart = request.getPart("imageUrl");
Part imageSumPart = request.getPart("imageSum");

// 폼으로부터 입력된 게시글 정보 가져오기
String title = request.getParameter("title");
String content = request.getParameter("content");
String titleNum = request.getParameter("titlenum");

System.out.println(title);
System.out.println(content);
System.out.println(titleNum);
// 현재 날짜를 생성일로 설정
java.util.Date currentDate = new java.util.Date();
Date createdAt = new Date(currentDate.getTime());

// 세션에서 사용자 번호 가져오기
int userNum = (int) session.getAttribute("usernum");
System.out.println(session.getAttribute("usernum"));

// DAO를 사용하여 데이터베이스에 게시글 정보 저장
PostDAO dao = new PostDAO(application);

int result = dao.createPost(title, content, titleNum, userNum, imageSumPart, imageUrlPart);


// 삽입된 게시물의 ID 가져오기

int lastInsertedPostId = dao.getLastPostId();
session.setAttribute("post_id", lastInsertedPostId);

dao.close();

if (result == 1) { // insert 성공
	// 작성한 게시글 목록 페이지로 리다이렉트
	response.sendRedirect("PostView.jsp?post_id=" + lastInsertedPostId);

} else {
	out.println("글쓰기에 실패하였습니다.");
}
%>

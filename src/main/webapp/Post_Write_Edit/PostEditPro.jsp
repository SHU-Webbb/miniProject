<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="post.PostDTO"%>
<%@ page import="java.sql.Date"%>

<%
// 게시글 ID를 파라미터로 받아옴
int postId = Integer.parseInt(request.getParameter("postId"));

// PostDAO를 사용하여 해당 게시글 정보를 가져옴
PostDAO dao = new PostDAO(application);
PostDTO post = dao.getPost(postId);

// 게시글이 존재하지 않을 경우 예외 처리
if (post == null) {
  System.out.println("게시글이 존재하지 않습니다.");
  return;
}

//이미지와 썸네일 Part 객체 가져오기
Part imageUrlPart = request.getPart("imageUrl");
Part imageSumPart = request.getPart("imageSum");

//폼으로부터 입력된 게시글 정보 가져오기
String title = request.getParameter("title");
String content = request.getParameter("content");
String titleNum = request.getParameter("titlenum");

System.out.println(title);
System.out.println(content);
System.out.println(titleNum);
//현재 날짜를 생성일로 설정
java.util.Date currentDate = new java.util.Date();
Date createdAt = new Date(currentDate.getTime());

//세션에서 사용자 번호 가져오기
int userNum = (int) session.getAttribute("usernum");
System.out.println(session.getAttribute("usernum"));

//DAO를 사용하여 데이터베이스에 게시글 정보 저장
int result = dao.updatePost(postId, title, content, titleNum, imageSumPart, imageUrlPart);

//삽입된 게시물의 ID 가져오기
int lastInsertedPostId = dao.getLastPostId();
session.setAttribute("post_id", lastInsertedPostId);

dao.close();

if (result == 1) { // update 성공
	// 수정된 게시글 보기 페이지로 리다이렉트
	response.sendRedirect("PostView.jsp?post_id=" + postId);
} else {
	out.println("수정에 실패하였습니다.");
}
%>

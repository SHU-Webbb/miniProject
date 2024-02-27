<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO" %>
<%@ page import="post.PostDTO" %>
<%@ page import="java.sql.Date" %>

<%
// 게시글 ID를 파라미터로 받아옴
int postId = Integer.parseInt(request.getParameter("post_id"));

// PostDAO를 사용하여 해당 게시글 정보를 가져옴
PostDAO dao = new PostDAO(application);
PostDTO post = dao.getPost(postId);

// 게시글이 존재하지 않을 경우 예외 처리
if (post == null) {
    System.out.println("게시글이 존재하지 않습니다.");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹툰 플랫폼</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body>
	<header>

		<a href="MainFrom.jsp"><h1>툰플릭스</h1></a>
		<%@ include file="Navbar.jsp"%>
	</header>

	<form method ="post" enctype="multipart/form-data" class="container post-edit" action="PostEditPro.jsp"
		method="post">
		<input type="hidden" name="postId" value="<%= postId %>"> <label
			for="title">제목:</label> <input type="text" id="title" name="title"
			value="<%= post.getTitle() %>"> <br> <label for="title">회차:</label>
		<input type="text" id="titlenum" name="titlenum"
			value="<%= post.getTitleNum() %>"> <br> <label
			for="content">내용:</label>
		<textarea id="content" name="content"><%= post.getContent() %></textarea>
		<br>  <label for="imageSum">썸네일 업로드:</label> <input type="file"
			id="imageSum" name="imageSum" value="<%= post.getImageSum() %>">
		<br><label for="imageUrl">웹툰 업로드:</label> <input type="file"
			id="imageUrl" name="imageUrl" value="<%= post.getImageUrl() %>">
		<br>
		<button type="submit">게시글 수정</button>
	</form>

	<footer>
		<p>&copy; 2024 웹툰 플랫폼</p>
	</footer>

    <script type="text/javascript" src="<%= request.getContextPath() %>/js/scripts.js"></script>
</body>
</html>

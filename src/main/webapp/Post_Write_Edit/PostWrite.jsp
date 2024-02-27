<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="utils.JSFunction"%>
<!-- 로그인 확인 -->
<!DOCTYPE html>
<%
if (session.getAttribute("usernum") == null) {
	JSFunction.alertLocation("로그인 후 이용해 주세요.", "../login/loginForm.jsp", out);
	return;
}
%>
<script>
	function validateForm(form) { // 폼 내용 검증
		if (form.title.value == "") {
			alert("제목을 입력하세요.");
			form.title.focus();
			return false;
		}
		if (form.content.value == "") {
			alert("내용을 입력하세요.");
			form.content.focus();
			return false;
		}
	}
</script>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/styles.css">
<head>
<meta charset="UTF-8">
<title>웹툰 플랫폼</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body>
	<div class="container">
		<header>
			<a href="MainFrom.jsp" id="toonflix-logo">
				<h1>툰플릭스</h1>
			</a>
			<%@ include file="Navbar.jsp"%>
		</header>
		<div class="postwrite">
			<h2>웹툰 올리기</h2>
			<hr>
			 <form method ="post" enctype="multipart/form-data" action="PostWritePro.jsp">
             <label for="title">제목:</label> <input type="text" id="title" name="title" ><br>
    		 <label for="titlenum">회차</label> <input type="text" id="titlenum" name="titlenum"><br>
   			 <label for="content">내용:</label> <textarea id="content" name="content"></textarea><br>
  		     <label for="imageSum">썸네일 업로드:</label> <input type="file" id="imageSum" name="imageSum" accept="img"><br>
   		     <label for="imageUrl">웹툰 업로드:</label> <input type="file" id="imageUrl" name="imageUrl" accept="img"><br>
    		 <button type="submit">작성 완료</button>
    		 <button type="reset">다시 작성</button>
    		 <button type="button" onclick="location.href='MainFrom.jsp';">목록 보기</button>
            </form>

		</div>
		<footer>
			<p>&copy; 2024 웹툰 플랫폼</p>
		</footer>

		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/scripts.js"></script>
</body>
</html>



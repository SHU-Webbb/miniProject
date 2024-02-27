<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import = "post.*" %>
<%@ page import = "java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>웹툰 플랫폼</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">

</head>
<body>
<div class="container">
    <header>
        <h1>툰플릭스</h1>
        <%@ include file="Navbar.jsp" %>
    </header>

    <section class="main-content">
        <!-- 웹툰 목록을 나타내는 부분 -->
        	<% %>
            <% 
            post.PostDAO postDAO = new post.PostDAO(application);
            List<post.PostDTO> webtoonList = postDAO.getMainImage();
            for (post.PostDTO webtoon : webtoonList) {
            	 String imageUrl = "../" + webtoon.getImageSum();
            	 System.out.println("Image URL: " + imageUrl);
        %>
            <a href="WebtoonList.jsp?usernum=<%=webtoon.getUserNum() %>" class="webtoon-card-link">
                <div class="webtoon-card">
                    <img src="..//img/<%= webtoon.getImageSum() %>" alt="<%= webtoon.getTitle() %>">
                    <h2><%= webtoon.getTitle() %></h2>
                    <p><%= webtoon.getTitleNum() %></p>
                    <p><%= webtoon.getContent() %></p>
                </div>
            </a>
            
        <% } %>
        
        
        <!-- 다른 웹툰 카드들 추가 -->
    </section>

    <footer>
        <p>&copy; 2024 웹툰 플랫폼</p>
    </footer>
</div>

    <script type="text/javascript" src="<%= request.getContextPath() %>/js/scripts.js"></script>
</body>
</html>

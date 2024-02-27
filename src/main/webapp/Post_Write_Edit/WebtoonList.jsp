<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="post.*" %>
<%@ page import="java.util.*" %>
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
        <a href="MainFrom.jsp"><h1>툰플릭스</h1></a>
        <%@ include file="Navbar.jsp" %>
    </header>

    <section class="main-content">
        <!-- 웹툰 목록을 나타내는 부분 -->
        <%
            post.PostDAO postDAO = new post.PostDAO(application);
            int Usernum = Integer.parseInt(request.getParameter("usernum"));
            System.out.println("usernum: " + Usernum);
            List<post.PostDTO> webtoonList = postDAO.getListByUserNum(Usernum);
            for (post.PostDTO webtoon : webtoonList) {
                String imageUrl = request.getContextPath() + webtoon.getImageSum();
                System.out.println("DB경로: " + webtoon.getImageSum());
                System.out.println("Image URL: " + imageUrl);
        %>

            <a href="PostView.jsp?post_id=<%= webtoon.getPostId() %>" class="webtoon-card-link">
                <div class="webtoon-card">
                    <p><%= webtoon.getTitleNum() %></p>
                </div>
            </a>

        <% } %>

    </section>

    <footer>
        <p>&copy; 2024 웹툰 플랫폼</p>
    </footer>
</div>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/scripts.js"></script>
</body>
</html>

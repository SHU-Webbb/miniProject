<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav>
    <button id="toggleNavBtn">네비게이션 토글</button>
    <ul id="navList">
        <li>
            <c:choose>
                <c:when test="${empty sessionScope.id}">
                    <a href="../login/loginForm.jsp" >로그인</a>
                </c:when>
                <c:otherwise>
                    <a href="#" onclick="showAlreadyLoggedInMessage()">로그인</a>
                </c:otherwise>
            </c:choose>
        </li>
        <li><a href="../login/logout.jsp" >로그아웃</a></li>
        <li><a href="../login/sighinForm.jsp" >회원가입</a></li>
        <li><a href="PostWrite.jsp" >웹툰 올리기</a></li>
        <li><a href="MainFrom.jsp" >메인페이지</a></li>
    </ul>
</nav>

<script>
    function showAlreadyLoggedInMessage() {
        alert("이미 로그인 되어 있습니다.");
    }
</script>

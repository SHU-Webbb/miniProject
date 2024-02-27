<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="login.SighInDTO" %>
<%@ page import="login.SighInDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입 처리</title>
    <script>
        function showAlert(message) {
            alert(message);
        }

        function navigateToMainPage() {
           window.history.back();
        }
        function navigateToMainPage2() {
            window.location.href = "../Post_Write_Edit/MainFrom.jsp";
        }
    </script>
</head>
<body>
<%
    // 폼에서 전송된 데이터 받기
    String id = request.getParameter("id");
    String password = request.getParameter("password");

    // DTO에 정보 설정
    SighInDTO member = new SighInDTO();
    member.setId(id);
    member.setPassword(password);

    // DAO를 통한 아이디 중복 확인 및 회원가입 처리
    SighInDAO memberDAO = new SighInDAO(application);
    boolean isDuplicate = memberDAO.checkDuplicateUsername(id);
    if (isDuplicate) {
%>
        <script>
            showAlert("이미 사용 중인 아이디입니다.");
            navigateToMainPage();
        </script>
<%
    } else {
        boolean isSuccess = memberDAO.registerMember(member);
        if (isSuccess) {
%>
            <script>
                showAlert("회원가입이 완료되었습니다.");
                navigateToMainPage2();
            </script>
<%
        } else {
%>
            <script>
                showAlert("사용할 수 있는 아이디 입니다.");
                navigateToMainPage();
            </script>
<%
        }
    }
%>
</body>
</html>
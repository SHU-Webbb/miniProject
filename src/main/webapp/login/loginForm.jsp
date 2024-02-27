<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 폼</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
<script>
function validateForm(form) {
    if (!form.id.value) {
        alert("아이디를 입력해주세요");
        form.user_id.focus();
        return false;
    }

    if (form.user_pw.value == "") {
        alert("비밀번호를 입력해주세요");
        form.user_pw.focus();
        return false;
    }

    return true;
}
</script>
</head>
<body>
    <h2>툰플릭스</h2>
    <%= request.getAttribute("LoginErrMsg") == null ?
                "" : request.getAttribute("LoginErrMsg")%>
    <%
        if(session.getAttribute("id") == null) {
    %>
    <form action="loginPro.jsp" method="post" name="loginFrm"
                onsubmit="return validateForm(this);">
        <label>ID
            <input type="text" name="id">
        </label><br>
        <label>PASS
            <input type="password" name="password">
        </label><br><br>
        <input type="submit" value="LOGIN">
        <a href="../login/sighinForm.jsp" class="main-form-link">회원가입</a>
        <a href="../Post_Write_Edit/MainFrom.jsp" class="main-form-link">메인화면</a>
    </form>

    <%
        } else { 
            response.sendRedirect("/MainFrom.jsp"); // 로그인 성공 시 MainForm.jsp로 redirect
        }
    %>

</body>
</html>
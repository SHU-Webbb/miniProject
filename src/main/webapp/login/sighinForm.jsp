<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>

 <link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/styles_signup.css">

</head>
<body>

    <h2>회원가입</h2>
    <form action="sighinPro.jsp" method="post" onsubmit="return checkUsername()">
        <label for="username">아이디:</label>
        <input type="text" id="id" name="id">
        <input type="submit" value="아이디 확인"><br><br>
   
        <label for="password">비밀번호:</label>
        <input type="password" id="password" name="password"><br><br>
  
        <input type="submit" value="회원가입" >
        
        <a href="../Post_Write_Edit/MainFrom.jsp" class="main-form-link">메인화면</a>

    
    <script>
        function checkUsername() {
            var username = document.getElementById("id").value;
            if (username.trim() === "") {
                alert("아이디를 입력하세요.");
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>

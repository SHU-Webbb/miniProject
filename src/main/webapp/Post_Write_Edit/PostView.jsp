<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="post.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹툰 플랫폼</title>
<link rel="stylesheet" type="text/css"
   href="<%=request.getContextPath()%>/css/styles.css">
<script>

window.onload = function() {
    var scrollPosition = sessionStorage.getItem("scrollPosition");
    if (scrollPosition !== null) {
        window.scrollTo(0, scrollPosition);
    }
};

window.onbeforeunload = function() {
    sessionStorage.setItem("scrollPosition", window.scrollY);
};

function PostDelete(postId) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        // AJAX를 사용하지 않고 페이지를 새로고침하는 방식
        var url = 'PostDelete.jsp?post_id=' + postId;
        window.location.href = url;
    }
}
function likeButtonClick(postId, userNum) {
    // AJAX를 사용하여 서버에 좋아요 이벤트 전송
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // 서버에서 처리 완료 시 좋아요 수 업데이트
                try {
                    var response = JSON.parse(xhr.responseText);
                    var likesCountDiv = document.getElementById("likesCountDiv");
                    likesCountDiv.innerHTML = "<p>좋아요 수: " + response.likesCount + "</p>";

                    var likeButton = document.getElementById("likeButton");
                    likeButton.innerHTML = response.isLiked ? "좋아요 취소" : "좋아요";
                    location.href="PostView.jsp?post_id=" + postId;
                } catch (error) {
                    console.error("JSON 파싱 오류: " + error);
                }
            } else {
                console.error("서버 응답 오류: " + xhr.status);
            }
        }
    };

    // 서버에 좋아요 이벤트 전송
    xhr.open("POST", "PostLikesServlet?post_id=" + postId + "&usernum=" + userNum, true);
    xhr.send();
    
    // 새로고침하는 코드를 추가
    setTimeout(function() {
        location.reload(); // 1초 후에 페이지를 새로고침
    }, 5000);
    
    // 로그인 여부 확인
    var isLoggedIn = <%= session.getAttribute("usernum") != null %>;

    if (!isLoggedIn) {
        // 로그인되어 있지 않으면 안내창 표시
        alert("로그인이 필요합니다.");
        // 로그인 페이지로 이동
        location.href = '/MiniPj/login/loginForm.jsp';
        return;
    }
    
    // 페이지 로드 시 스크롤 위치 저장
    window.onload = function() {
        var scrollPosition = localStorage.getItem("scrollPosition");
        if (scrollPosition !== null) {
            window.scrollTo(0, scrollPosition);
        }
    };

    // 페이지 언로드 시 스크롤 위치 저장
    window.onbeforeunload = function() {
        localStorage.setItem("scrollPosition", window.scrollY);
    };
}

</script>

</head>
<body>
   <div class="container">
      <header>

         <a href="MainFrom.jsp"><h1>툰플릭스</h1></a>
         <%@ include file="Navbar.jsp"%>
         <%
         // 게시글 ID를 파라미터로 받아옴
         // PostDAO를 사용하여 해당 게시글 정보를 가져옴
         PostDAO dao = new PostDAO(application);
         int PostId = Integer.parseInt(request.getParameter("post_id"));
         System.out.println("게시글 ID: " + PostId);

         //System.out.println("댓글 id: " + commentId);
         PostDTO post = dao.getPost(PostId);

         // CommentDAO를 사용하여 댓글 목록을 가져옴
         CommentDAO commentDAO = new CommentDAO(application);
         List<CommentDTO> comments = commentDAO.getCommentsForPost(PostId);

         // 가져온 댓글 목록을 request에 추가
         request.setAttribute("comments", comments);

         //로그인한 사용자와 게시글 작성자가 동일한 경우에만 수정과 삭제 버튼을 보여줌
         %>
         <%
         if (session.getAttribute("usernum") != null && session.getAttribute("usernum").equals(post.getUserNum())) {
         %>
         <button type="button"
            onclick="location.href='PostEdit.jsp?post_id=<%=post.getPostId()%>';">수정하기</button>
         <button type="button" onclick="PostDelete(<%=post.getPostId()%>);">삭제하기</button>
         <%
         }
         %>
         <button type="button" onclick="location.href='WebtoonList.jsp?usernum=<%=post.getUserNum() %>'">목록 보기</button>
      </header>

      <section class="main-content">
         <!-- 웹툰 목록을 나타내는 부분 -->
         <%
         post.PostDAO postDAO = new post.PostDAO(application);
         String postIdFromWebtoonList = request.getParameter("post_id");
         int postId = Integer.parseInt(postIdFromWebtoonList);
         List<post.PostDTO> webtoonList = postDAO.getPostViewData(postId);
         for (post.PostDTO webtoon : webtoonList) {
            System.out.println("DB경로: " + webtoon.getImageUrl());
            System.out.println("post_id: " + postIdFromWebtoonList);
         %>
         <a class="webtoon-card-link-view">
            <div class="webtoon-card">
               <img src="..//img/<%=webtoon.getImageUrl()%>"
                  alt="<%=webtoon.getTitle()%>">
            </div>
         </a>

         <%
         }
         %>
      </section>
      <!-- 좋아요 영역  -->
       <button class="like-button" id="likeButton"
                onclick="likeButtonClick(<%=post.getPostId()%>, <%=session.getAttribute("usernum")%>);">좋아요</button>

            <div id="likesCountDiv">

                <%
                int webtoonPostId = Integer.parseInt(postIdFromWebtoonList);
                int userNum = (session.getAttribute("usernum") != null) ? (int) session.getAttribute("usernum") : 0;
                PostLikesDAO postLikesDAO = new PostLikesDAO(application);
                PostLikesDTO likesDTO = postLikesDAO.getLikesCount();
                %>

                <p>
                    좋아요 수:
                    <%=likesDTO.getLikesCount()%></p>

            </div>

      <!-- 댓글 영역  -->
      <div class="comments-container">
   

    <!-- 저장된 댓글 목록 -->
    <div class="comment-list">
        <c:forEach var="comment" items="${comments}">
            <div class="comment">
               <span class = "comment-id"> ${comment.id} </span> <br>
                <span class = "comment-content">${comment.content}</span> 
                <!-- 댓글 삭제 폼 -->
                <c:if test="${sessionScope.usernum ne null && sessionScope.usernum eq comment.userNum}">
                    <!-- 로그인한 경우에만 삭제 링크 표시 -->
                    <form id="commentForm" action="CommentPro.jsp" method="post">
                        <input type="hidden" id="action" name="action" value="delete">
                        <input type="hidden" id="postId" name="postId" value="${param.post_id}">
                        <input type="hidden" id="commentId" name="commentId" value="${comment.commentId}"><input type="submit" value="삭제">
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>

 <!-- 댓글 입력 폼 -->
    <div class="comment-form">
        <c:if test="${sessionScope.usernum ne null}">
            <!-- 로그인한 경우에만 댓글 작성 폼 표시 -->
            <form id="commentForm" action="CommentPro.jsp" method="post">
                <input type="hidden" id="action" name="action" value="add">
                <input type="hidden" id="postId" name="postId" value="${param.post_id}">
                <textarea name="content" rows="3" cols="50" required></textarea>
                <input type="submit" value="댓글 작성">
            </form>
        </c:if>
        <c:if test="${sessionScope.usernum eq null}">
            <!-- 로그인하지 않은 경우 로그인 페이지로 이동 -->
            <p>
                <a href="../login/loginForm.jsp">로그인</a> 후 댓글을 작성할 수 있습니다.
            </p>
        </c:if>
    </div>
      <footer>
         <p>&copy; 2024 웹툰 플랫폼</p>
      </footer>
   </div>

   <script type="text/javascript"
      src="<%=request.getContextPath()%>/js/scripts.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="post.PostDAO"%>
<%@ page import="java.util.*"%>
<%@ page import="post.PostDTO"%>
<%@ page import="common.JDBConnect"%>

<%
// 게시물 ID를 파라미터로 받아옴
int postId = Integer.parseInt(request.getParameter("post_id"));

// PostDAO를 사용하여 해당 게시물을 삭제
PostDAO dao = new PostDAO(application);
PostDTO dto = new PostDTO();

int result = dao.deletePost(postId);


// 삭제가 성공적으로 이루어졌으면 목록 페이지로 리다이렉트
if (result > 0) {
    response.sendRedirect("WebtoonList.jsp?usernum=" + session.getAttribute("usernum"));
} else {
    out.println("게시물 삭제에 실패했습니다.");
}
%>

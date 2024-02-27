<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="post.*"%>
<%@ page import="java.util.*"%>
<%@ page import="post.*"%>

<%
   //요청으로부터 전달받은 액션과 게시글 id 파라미터를 가져옴
    String action = request.getParameter("action");
    int postId = Integer.parseInt(request.getParameter("postId"));
    System.out.println(postId);
    //객체 생성
    CommentDAO commentDAO = new CommentDAO(application);

    if ("add".equals(action)) {
      //댓글 추가 액션이면  
      if (session.getAttribute("usernum") != null) {
        // 사용자가 로그인한 경우에만 댓글 추가 작업을 수행    
        String content = request.getParameter("content");
        
        //새로운 객체 생성 후 댓글 추가  
        CommentDTO newComment = new CommentDTO(0, (int)session.getAttribute("usernum"), postId, content);
            
        commentDAO.addComment(newComment);

            
        //댓글이 추가된 후 해당 게시글의 댓굴 목록을 다시 가져옴   
        List<CommentDTO> comments = commentDAO.getCommentsForPost(postId);

        //request 객체에 댓글 목록을 추가   
        request.setAttribute("comments", comments);
        
        // PostView.jsp 로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("PostView.jsp?post_id=" + postId);
           dispatcher.forward(request, response);
        } else {
            //사용자가 로그인 하지 않은 경우 로그인 페이지로 리다이렉트
            response.sendRedirect("../login/loginForm.jsp");
        }
    } else if ("delete".equals(action)) {
      
      //댓글 삭제 액션이면
        int commentId = Integer.parseInt(request.getParameter("commentId"));
      //댓글 삭제 작업을 수행 
        commentDAO.deleteComment(commentId);

      //댓글이 삭제된 후에 해당 게시글의 댓글 목록을 다시 가져옴.
        List<CommentDTO> comments = commentDAO.getCommentsForPost(postId);

       // request 객체에 댓글 목록을 추가
        request.setAttribute("comments", comments);

        //PostView로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("PostView.jsp?post_id=" + postId);
        dispatcher.forward(request, response);
    }
%>
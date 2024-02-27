package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import post.PostLikesDAO;


@WebServlet("/PostLikesServlet")
public class PostLikesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 클라이언트로부터 전달된 데이터 가져오기
        int postId = Integer.parseInt(request.getParameter("post_id"));
        int userNum = Integer.parseInt(request.getParameter("usernum"));

        // PostLikesDAO를 사용하여 좋아요 처리
        PostLikesDAO postLikesDAO = new PostLikesDAO(getServletContext());
        postLikesDAO.addLike(postId, userNum);

        // 좋아요 수를 다시 가져와서 클라이언트에 응답
        int likesCount = postLikesDAO.getLikesCount().getLikesCount();

        // JSON 형태의 응답 생성
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"likesCount\":" + likesCount + "}");
        out.flush();
    }
}
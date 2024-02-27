package servlet;
//WebtoonListServlet.java
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import post.*;

import java.util.List;

@WebServlet("/webtoonList")
public class WebtoonListServlet extends HttpServlet {
 protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
     // DAO를 호출하여 웹툰 목록을 가져옴
     PostDAO postDAO = new PostDAO(getServletContext());
     List<PostDTO> webtoonList = postDAO.getMainImage();

     // 웹툰 목록을 요청 범위 속성으로 설정
     request.setAttribute("webtoonList", webtoonList);

     // JSP 페이지로 요청을 전달하여 렌더링
     request.getRequestDispatcher("/WEB-INF/views/webtoonList.jsp").forward(request, response);
 }
}

package servlet;

import jakarta.servlet.*;
import post.PostLikesDAO;


public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 웹 애플리케이션이 시작될 때 호출되는 메서드
        ServletContext servletContext = sce.getServletContext();
        
        // PostLikesDAO 객체를 생성하여 connection 초기화
        PostLikesDAO postLikesDAO = new PostLikesDAO(servletContext);
        servletContext.setAttribute("postLikesDAO", postLikesDAO);
        
        System.out.println("웹 애플리케이션 초기화 완료!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 웹 애플리케이션이 종료될 때 호출되는 메서드
        System.out.println("웹 애플리케이션 종료!");
    }
}

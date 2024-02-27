package common;

import java.sql.*;

import jakarta.servlet.ServletContext;

public class JDBConnect {

  public Connection conn = null; // jdbc에서 오라클 DB를 접속한 객체 저장
  public Statement stmt = null; // SQL문 저장하고 실행하는 객체
  public PreparedStatement pstmt = null;
  public ResultSet rs = null;

  public JDBConnect() {
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String uid = "mini_user";
    String pass = "ora123";

    try {
      // (1)오라클 JDBC 드리아버를 JVM 메모리로 로드
      Class.forName("oracle.jdbc.driver.OracleDriver");

      // (2)오라클 데이터베이스에 연결
      conn = DriverManager.getConnection(url, uid, pass);

      System.out.println("DB 연결 성공(기본 생성자)");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public JDBConnect(String driver, String url, String id, String pwd) {

    try {
      // (1)오라클 JDBC 드리아버를 JVM 메모리로 로드
      Class.forName("oracle.jdbc.driver.OracleDriver");

      // (2)오라클 데이터베이스에 연결
      conn = DriverManager.getConnection(url, id, pwd);

      System.out.println("DB 연결 성공(매개변수가 있는 생성자)");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 세번째 생성자
  public JDBConnect(ServletContext application) {
  
    try {
      // JDBC 드라이버 로드
      String driver = application.getInitParameter("OracleDriver");
      Class.forName(driver);
      
      //DB에 연결
      String url = application.getInitParameter("OracleURL");
      String uid = application.getInitParameter("OracleId");
      String pwd = application.getInitParameter("OraclePwd");
      conn = DriverManager.getConnection(url,uid,pwd);
      
      System.out.println("DB 연결 성공!!");
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  // DB 연결 해지
  public void close() {
    try {
      if (rs != null)
        rs.close();
      if (stmt != null)
        stmt.close();
      if (pstmt != null)
        pstmt.close();
      if (conn != null)
        conn.close();

      System.out.println("DB 연결 해지");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 새로운 데이터베이스 연결을 초기화하는 메소드
  public void connect() {
      String url = "jdbc:oracle:thin:@localhost:1521:XE";
      String uid = "mini_user";
      String pass = "ora123";

      try {
          // Oracle JDBC 드라이버를 로드
          Class.forName("oracle.jdbc.driver.OracleDriver");

          // Oracle 데이터베이스에 연결
          conn = DriverManager.getConnection(url, uid, pass);

          System.out.println("DB 연결 성공");
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}

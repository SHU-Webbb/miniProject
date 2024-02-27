package webtoonplatform.dao;

import common.JDBConnect;
import jakarta.servlet.ServletContext;
import webtoonplatform.dto.loginDTO;


public class loginDAO extends JDBConnect {

    public loginDAO(ServletContext application) {
        super(application);
    }

    public loginDAO(String driver, String url, String id, String pwd) {
        super(driver, url, id, pwd);
    }

    public loginDTO getloginDTO(String uid, String upass) {
    	String sql = "SELECT * FROM users WHERE id=? and password=?";
        loginDTO dto = new loginDTO();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uid);
            pstmt.setString(2, upass);
                     
            rs = pstmt.executeQuery();

            if (rs.next()) {
                
            	dto.setUserNum(rs.getInt("usernum")); 
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(); // 수정: close 메서드는 더이상 static이 아니므로 this.close() 대신 close()로 호출
        }

        return dto;
    }
}

package login;

import common.JDBConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SighInDAO extends JDBConnect {

    // 생성자를 통해 Connection 초기화
    public SighInDAO(ServletContext application) {
        super(application); // JDBConnect의 생성자 호출하여 Connection 초기화
    }

    // 아이디 중복 확인 메서드
    public boolean checkDuplicateUsername(String id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 회원 등록 메서드
    public boolean registerMember(SighInDTO member) {
        if (checkDuplicateUsername(member.getId())) {
            System.out.println("이미 존재하는 아이디입니다.");
            return false;
        }

        String sql = "INSERT INTO users (usernum, id, password) VALUES (SEQ_POSTS_NUM.NEXTVAL, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPassword());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package post;

import common.JDBConnect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;  // Import jakarta.servlet.ServletContext for ServletContext

public class PostLikesDAO extends JDBConnect {

    public PostLikesDAO(ServletContext application) {
        super(application);
    }

    // 이미 좋아요를 했는지 확인하는 메서드
    private boolean isLiked(int postId, int userNum) {
        try {
            String sql = "SELECT COUNT(*) FROM post_likes WHERE post_id = ? AND usernum = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userNum);
            rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            pstmt.close();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 좋아요 취소 메서드
    private void dislikePost(int postId, int userNum) {
        try {
            String sql = "DELETE FROM post_likes WHERE post_id = ? AND usernum = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userNum);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 좋아요 수 가져오는 메서드
    public PostLikesDTO getLikesCount() {
        PostLikesDTO likesDTO = new PostLikesDTO();
        try {
            String sql = "SELECT COUNT(*) FROM post_likes";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            int likesCount = rs.getInt(1);
            likesDTO.setLikesCount(likesCount);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likesDTO;
    }
    
    public void addLike(int postId, int userNum) {
        try {
            // 이미 좋아요를 했는지 확인
            if (isLiked(postId, userNum)) {
                // 이미 좋아요를 했다면 좋아요 취소
                dislikePost(postId, userNum);
            } else {
                // 좋아요를 하지 않았다면 좋아요 처리
                String sql = "INSERT INTO post_likes (post_id, usernum) VALUES (?, ?)";
                conn.setAutoCommit(false); // 트랜잭션 시작
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, postId);
                    pstmt.setInt(2, userNum);
                    pstmt.executeUpdate();
                    conn.commit(); // 트랜잭션 커밋
                } catch (SQLException e) {
                    conn.rollback(); // 트랜잭션 롤백
                    e.printStackTrace();
                } finally {
                    conn.setAutoCommit(true); // 트랜잭션 종료
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}

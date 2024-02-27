package post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

public class CommentDAO extends JDBConnect {
	// DB연결 작업 처리

	private ServletContext application;
	private int postId;
	private int commentId;

	public CommentDAO(ServletContext application, int postId, int commentId) {
		super(application);
		this.postId = postId;
		this.commentId = commentId;
	}

	public CommentDAO(ServletContext application) {

		super(application); // JDBConnect(application) 생성자 호출
	}

//댓글 추가
	public void addComment(CommentDTO comment) {
		try {

			if (isPostExist(comment.getPostId())) {
				String query = "INSERT INTO comments (comment_id, usernum, post_id, content) VALUES (seq_comments_num.NEXTVAL, ?, ?, ?)";
				try (PreparedStatement pstmt = conn.prepareStatement(query)) {
					pstmt.setInt(1, comment.getUserNum());
					pstmt.setInt(2, comment.getPostId());
					pstmt.setString(3, comment.getContent());
					pstmt.executeUpdate();
				}
			} else {
				System.out.println("해당하는 post_id가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//댓글을 추가하기 전에 해당하는 post_id가 존재하는지 확인하는 메서드
	private boolean isPostExist(int postId) {
		try {
			String query = "SELECT COUNT(*) FROM posts WHERE post_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, postId);
				try (ResultSet resultSet = pstmt.executeQuery()) {
					if (resultSet.next()) {
						int count = resultSet.getInt(1);
						return count > 0; // post_id가 존재하면 true 반환
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // 오류가 발생하거나 post_id가 존재하지 않으면 false 반환
	}

	// 댓글 삭제
	public void deleteComment(int commentId) {
		try {
			String query = "DELETE FROM comments WHERE comment_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, commentId);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//해당 게시글에 대한 댓글 목록 가져오기
	public List<CommentDTO> getCommentsForPost(int postId) {
		List<CommentDTO> comments = new ArrayList<>();
		try {
			String query = "SELECT c.*, u.id as id FROM comments c JOIN users u ON c.usernum = u.usernum WHERE c.post_id =?"; //users 테이블에서 id 가져옴
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, postId);
				try (ResultSet resultSet = pstmt.executeQuery()) {
					while (resultSet.next()) {
						CommentDTO comment = new CommentDTO(resultSet.getInt("comment_id"), resultSet.getInt("usernum"),
								resultSet.getInt("post_id"), resultSet.getString("content"),
								resultSet.getTimestamp("created_at"), resultSet.getTimestamp("updated_at"),resultSet.getString("id"));
						comments.add(comment);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
}
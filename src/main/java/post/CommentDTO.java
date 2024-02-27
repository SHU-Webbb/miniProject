package post;

import java.sql.Timestamp;

public class CommentDTO {

	private int commentId;
	private int userNum;
	private int postId;
	private String content;
	private String id; // id 필드 추가

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//모든 필드를 초기화하는 생성자
	public CommentDTO(int commentId, int userNum, int postId, String content) {
		this.commentId = commentId;
		this.userNum = userNum;
		this.postId = postId;
		this.content = content;
	}

	public CommentDTO(int commentId, int userNum, int postId, String content, Timestamp createdAt,
			Timestamp updatedAt, String id) {
		this.commentId = commentId;
		this.userNum = userNum;
		this.postId = postId;
		this.content = content;
		this.id = id;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

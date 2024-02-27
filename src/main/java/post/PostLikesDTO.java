package post;

public class PostLikesDTO {
	private int postId;
	private int userNum;
	private int likesCount;
	
    // 기본 생성자
    public PostLikesDTO() {
    }
	
    public PostLikesDTO(int postId, int userNum, int likesCount) {
        this.postId = postId;
        this.userNum = userNum;
        this.likesCount = likesCount;
    }
    
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}
}

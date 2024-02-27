package post;

import java.util.Date;

public class PostDTO {
    private int post_id;
    private int userNum;
    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private String imageSum;
    private Date createdAt;
    private Date updatedAt;
    private String titleNum;
    
    
    
    public String getTitleNum() {
		return titleNum;
	}
	public void setTitleNum(String titleNum) {
		this.titleNum = titleNum;
	}
	public int getPostId() {
      return post_id;
    }
    public void setPostId(int postId) {
      this.post_id = postId;
    }
    public int getUserNum() {
      return userNum;
    }
    public void setUserNum(int userNum) {
      this.userNum = userNum;
    }
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
    public String getContent() {
      return content;
    }
    public void setContent(String content) {
      this.content = content;
    }
    public String getImageUrl() {
      return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
    }
    public Date getCreatedAt() {
      return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
      return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
    }
    
    public String getId() {
      return id;
    }
    public void setId(String Id) {
      this.id = Id;
    }
    public int getPost_id() {
      return post_id;
    }
    public void setPost_id(int post_id) {
      this.post_id = post_id;
    }
    public String getImageSum() {
      return imageSum;
    }
    public void setImageSum(String imageSum) {
      this.imageSum = imageSum;
    }
    
    
}

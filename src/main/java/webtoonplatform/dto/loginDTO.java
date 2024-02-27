package webtoonplatform.dto;

public class loginDTO {
	private int usernum;
	private String id;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUsernum() {
		return usernum;
	}
	public void setUserNum(int usernum) {
		this.usernum = usernum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

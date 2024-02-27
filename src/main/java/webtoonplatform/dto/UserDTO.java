package webtoonplatform.dto;

public class UserDTO {
	private int usernum;
	private String id;
	private String password;
	public int getUsernum() {
		return usernum;
	}
	public void setUsernum(int usernum) {
		this.usernum = usernum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserDTO [usernum=" + usernum + ", id=" + id + ", password=" + password + "]";
	}
	
	
}
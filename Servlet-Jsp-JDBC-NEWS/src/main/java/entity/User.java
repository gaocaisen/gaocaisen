package entity;

import java.io.Serializable;

/**
 * 用户信息的实体类
 * 
 * @author 28049
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = -2955843480862637722L;
	private Integer uId;
	private String username;
	private String password;
	private Integer dId;
	private Integer admin;
	private String dName;

	public User() {
		super();
	}

	public User(Integer uId, String username, String password, Integer dId, Integer admin, String dName) {
		super();
		this.uId = uId;
		this.username = username;
		this.password = password;
		this.dId = dId;
		this.admin = admin;
		this.dName = dName;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", username=" + username + ", password=" + password + ", dId=" + dId + ", admin="
				+ admin + ", dName=" + dName + "]";
	}

}

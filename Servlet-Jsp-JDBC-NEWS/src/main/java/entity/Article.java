package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章的实体类
 * 
 * @author admin
 *
 */
public class Article implements Serializable {
	private static final long serialVersionUID = 8215905094618490328L;
	private Integer aid;
	private Integer uId;
	private String title;
	private String article;
	private Date date;
	private Integer mid;
	private String mName;
	private Integer account;
	private String image;
	private String username;
	
	public Article() {
		super();
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "Article [aid=" + aid + ", uId=" + uId + ", title=" + title + ", article=" + article + ", date=" + date
				+ ", mid=" + mid + ", mName=" + mName + ", account=" + account + ", image=" + image + ", username="
				+ username + "]";
	}
}

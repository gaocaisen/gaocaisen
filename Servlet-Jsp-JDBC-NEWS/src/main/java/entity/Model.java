package entity;

import java.io.Serializable;

/**
 * 栏目信息的实体类
 * 
 * @author 28049
 *
 */
public class Model implements Serializable {
	private static final long serialVersionUID = 3716128401041713686L;
	private Integer mid;
	private String mName;
	private Integer sMid;

	public Model() {
		super();
	}

	public Model(Integer mid, String mName, Integer sMid) {
		super();
		this.mid = mid;
		this.mName = mName;
		this.sMid = sMid;
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

	public Integer getsMid() {
		return sMid;
	}

	public void setsMid(Integer sMid) {
		this.sMid = sMid;
	}

	@Override
	public String toString() {
		return "Model [mid=" + mid + ", mName=" + mName + ", sMid=" + sMid + "]";
	}
}

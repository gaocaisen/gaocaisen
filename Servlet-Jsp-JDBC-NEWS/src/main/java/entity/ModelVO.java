package entity;

import java.io.Serializable;
/**
 * À¸Ä¿µÄVOÀà
 * @author 28049
 *
 */
public class ModelVO implements Serializable{
	private static final long serialVersionUID = 695766057894583063L;
	private Integer mid;
	private String mName;
	private String smName;
	private Integer sMid;
	
	public ModelVO() {
		super();
	}

	public ModelVO(Integer mid, String mName, String smName, Integer sMid) {
		super();
		this.mid = mid;
		this.mName = mName;
		this.smName = smName;
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

	public String getSmName() {
		return smName;
	}

	public void setSmName(String smName) {
		this.smName = smName;
	}

	public Integer getsMid() {
		return sMid;
	}

	public void setsMid(Integer sMid) {
		this.sMid = sMid;
	}

	@Override
	public String toString() {
		return "ModelVO [mid=" + mid + ", mName=" + mName + ", smName=" + smName + ", sMid=" + sMid + "]";
	}

}

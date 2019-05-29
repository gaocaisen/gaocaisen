package entity;

import java.io.Serializable;

/**
 * 部门表的实体类
 * @author admin
 *
 */
public class Department implements Serializable{
	private static final long serialVersionUID = -9031465479847663533L;
	private Integer dId;
	private String dName;
	private Integer sId;
	
	public Department() {
		super();
	}

	public Department(Integer dId, String dName, Integer sId) {
		super();
		this.dId = dId;
		this.dName = dName;
		this.sId = sId;
	}

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	@Override
	public String toString() {
		return "DepartMent [dId=" + dId + ", dName=" + dName + ", sId=" + sId + "]";
	}
}

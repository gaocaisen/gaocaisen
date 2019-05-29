package dept;

import java.util.List;

import org.junit.Test;

import dao.DeptDAO;
import entity.Department;

public class Test1 {
	
	@Test
	public void addDept() {
		DeptDAO dao = new DeptDAO();
		Department dept = new Department();
		dept.setdName("电务公司");
		dept.setsId(1);
		String id = dao.addDept(dept);
		System.out.println(id);
	}
	
	@Test
	public void delete() {
		DeptDAO dao = new DeptDAO();
		int dId = 4;
		boolean flag = dao.delete(dId );
		System.out.println(flag);
	}
	
	@Test
	public void changeDept() {
		DeptDAO dao = new DeptDAO();
		int dId = 3;
		String dName = "电务公司";
		boolean flag = dao.changeDept(dId, dName);
		System.out.println(flag);
	}
	
	@Test
	public void listDept() {
		DeptDAO dao = new DeptDAO();
		List<Department> list = dao.listDept();
		for (Department department : list) {
			System.out.println(department);
		}
	}
	
	@Test
	public void getDeptBySid() {
		DeptDAO dao = new DeptDAO();
		int sId = 1;
		List<Department> list = dao.getDeptsBySid(sId );
		for (Department department : list) {
			System.out.println(department);
		}
	}

}

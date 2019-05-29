package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;
import util.DBUtils;

public class DeptDAO {
	
	/**
	 * 添加部门
	 * @param dept 部门信息
	 */
	public String addDept(Department dept) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "insert into t_dept values(null,?,?)";
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, dept.getdName());
			ps.setInt(2, dept.getsId());
			int rows = ps.executeUpdate();
			if(rows>0) {
				rs = ps.getGeneratedKeys();
				while(rs.next()) {
					return rs.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return "";
	}
	
	/**
	 * 根据部门id删除部门
	 * @param dId 部门id
	 * @return true代表删除成功，false代表删除失败
	 */
	public boolean delete(int dId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "delete from t_dept where dId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dId);
			int rows = ps.executeUpdate();
			if(rows>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return false;
	}
	
	/**
	 * 根据部门id修改部门信息
	 * @param dId 部门id
	 * @param dept 要修改的部门信息
	 * @return true代表修改成功，false代表修改失败
	 */
	public boolean changeDept(int dId,String dName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "update t_dept set dName=? where dId=?";
		 	ps = conn.prepareStatement(sql);
			ps.setString(1, dName);
			ps.setInt(2, dId);
			int rows = ps.executeUpdate();
			if(rows>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return false;
	}
	
	/**
	 * 查询有哪些部门
	 * @return 部门的集合
	 */
	public List<Department> listDept() {
		List<Department> list = new ArrayList<Department>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_dept order by dId";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Department dept = new Department();
				dept.setdId(rs.getInt(1));
				dept.setdName(rs.getString(2));
				dept.setsId(rs.getInt(3));
				list.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 根据sId查询有哪些部门
	 * @param sId 上级部门id
	 * @return 部门集合
	 */
	public List<Department> getDeptsBySid(int sId){
		List<Department> list = new ArrayList<Department>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_dept where sId=? order by dId";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Department dept = new Department();
				dept.setdId(rs.getInt(1));
				dept.setdName(rs.getString(2));
				dept.setsId(rs.getInt(3));
				
				list.add(dept);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return list;
	}
	
	/**
	 * 根据dId查询部门信息
	 * @param dId 部门id
	 * @return 包含部门信息的对象
	 */
	public Department getDeptsByDid(int dId){
		Department dept = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_dept where dId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dId);
			rs = ps.executeQuery();
			while(rs.next()) {
				dept = new Department();
				dept.setdId(rs.getInt(1));
				dept.setdName(rs.getString(2));
				dept.setsId(rs.getInt(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return dept;
	}
	
	
	
}

















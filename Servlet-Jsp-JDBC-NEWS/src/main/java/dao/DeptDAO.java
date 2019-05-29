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
	 * ��Ӳ���
	 * @param dept ������Ϣ
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
	 * ���ݲ���idɾ������
	 * @param dId ����id
	 * @return true����ɾ���ɹ���false����ɾ��ʧ��
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
	 * ���ݲ���id�޸Ĳ�����Ϣ
	 * @param dId ����id
	 * @param dept Ҫ�޸ĵĲ�����Ϣ
	 * @return true�����޸ĳɹ���false�����޸�ʧ��
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
	 * ��ѯ����Щ����
	 * @return ���ŵļ���
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
	 * ����sId��ѯ����Щ����
	 * @param sId �ϼ�����id
	 * @return ���ż���
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
	 * ����dId��ѯ������Ϣ
	 * @param dId ����id
	 * @return ����������Ϣ�Ķ���
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

















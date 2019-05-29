package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.DBUtils;

public class UserDAO {
	/**
	 * ���ǵ�¼�ķ���
	 * @param username �û���
	 * @param password ����
	 * @return true�����¼�ɹ� false�����¼ʧ��
	 */
	public User checkLogin(String username,String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		String sql = "select * from t_user where username=? and password=?";
		try {
			conn = DBUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setuId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setdId(rs.getInt(4));
				user.setAdmin(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return user;
	}
	

	/**
	 * ����û� 
	 * @param user �û�
	 * @return true������ӳɹ���false�������ʧ��
	 */
	public boolean addUser(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "insert into t_user values(null,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getdId());
			ps.setInt(4, 0);
			
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
	 * ����uidɾ���û�
	 * @param uId �û�id
	 * @return true����ɾ���ɹ���false����ɾ��ʧ��
	 */
	public boolean delUser(int uId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "delete from t_user where uId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uId);
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
	 * �����û�uId���޸��û���Ϣ
	 * @param uId �û�id
	 * @param user �û���Ϣ
	 * @return true�����޸ĳɹ���false�����޸�ʧ��
	 */
	public boolean modifyUser(int uId,User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "update t_user set username=?,password=?,dId=? where uId=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getdId());
			ps.setInt(4, uId);
			
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
	 * ��ѯ�û��б�
	 * @return
	 */
	public List<User> listUser(int count) {
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user order by uId limit ?,10";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (count-1)*10);
			rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setuId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setdId(rs.getInt(4));
				user.setAdmin(rs.getInt(5));
				
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * �����û�id��ѯ�û�
	 * @param id �û�id
	 * @return �û���Ϣ
	 */
	public User getUserByUid(int uId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user u join t_dept d on u.dId=d.dId where uId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uId);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setuId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setdId(rs.getInt(4));
				user.setAdmin(rs.getInt(5));
				user.setdName(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.close(conn, ps, rs);
		}
		
		return user;
	}
	
	/**
	 * ���ݲ���id��ѯ��ǰ��������ЩԱ��
	 * @return
	 */
	public List<User> getUserByDid(int dId){
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user where dId=? order by uId";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,dId);
			rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setuId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setdId(rs.getInt(4));
				user.setAdmin(rs.getInt(5));
				
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * �����û����ж��û��Ƿ��Ѿ�����
	 * @param username
	 * @return true�����Ѵ��ڣ�false��������
	 */
	public boolean getUserByUAP(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.close(conn, ps, rs);
		}
		
		return false;
	}
	
	/**
	 * ��ѯ�û������ж���������
	 * @return ������
	 */
	public int getTotalRowsFromUser() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows = 0;
		try {
			conn = DBUtils.getConn();
			String sql = "select count(1) from t_user";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.close(conn, ps, rs);
		}
		
		return rows;
	}
	

}










package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Article;
import util.DBUtils;

public class ArticleDAO {
	
	/**
	 * ��������
	 * @param art ��װ������������Ϣ�����¶���
	 * @return �������µ�����ֵ
	 */
	public String addArticle(Article art) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "insert into t_article"
					+ "(aid,uId,title,article,date,mid,account,image) values"
					+ "(null,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, art.getuId());
			ps.setString(2, art.getTitle());
			ps.setString(3, art.getArticle());
			ps.setDate(4, new Date(System.currentTimeMillis()));
			ps.setInt(5, art.getMid());
			ps.setInt(6, 0);
			ps.setString(7, art.getImage());
			
			int rows = ps.executeUpdate();
			if(rows>0) {
				rs = ps.getGeneratedKeys();
				while(rs.next()) {
					//����������ֵ
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
	 * ��������idɾ������
	 * @param aid ����id
	 * @return true����ɾ���ɹ���false����ɾ��ʧ��
	 */
	public boolean delArticle(int aid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "delete from t_article where aid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, aid);
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
	 * ��������id�޸�����
	 * @param aid ����id
	 * @param art ������Ҫ�޸����ݵĶ���
	 * @return true�����޸ĳɹ���false�����޸�ʧ��
	 */
	public boolean changeArticle(int aid,Article art) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "update t_article set title=?,article=?,date=?,"
					+ "mid=?,image=? where aid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, art.getTitle());
			ps.setString(2, art.getArticle());
			ps.setDate(3, new Date(System.currentTimeMillis()));
			ps.setInt(4, art.getMid());
			ps.setString(5, art.getImage());
			ps.setInt(6, aid);
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
	 * ��ѯ���е�����
	 * @return ���µļ���
	 */
	public List<Article> listArticle(int count){
		List<Article> list = new ArrayList<Article>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select aid,a.uId,title,article,date,a.mid,account,"
					+ "image,m.mName,u.username from t_model m join t_article a "
					+ "on m.mid=a.mid join t_user u on a.uId=u.uId "
					+ "order by date desc limit ?,?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (count-1)*10);
			ps.setInt(2, 10);
			rs = ps.executeQuery();
			while(rs.next()) {
				Article art = new Article();
				art.setAid(rs.getInt(1));
				art.setuId(rs.getInt(2));
				art.setTitle(rs.getString(3));
				art.setArticle(rs.getString(4));
				art.setDate(rs.getDate(5));
				art.setMid(rs.getInt(6));
				art.setAccount(rs.getInt(7));
				art.setImage(rs.getString(8));
				art.setmName(rs.getString(9));
				art.setUsername(rs.getString(10));
				
				list.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return list;
		
	}
	
	
	/**
	 * ��������aid��ѯ���¾�����Ϣ
	 * @param aid ����id
	 * @return ��װ���¾�����Ϣ�Ķ���
	 */
	public Article getArticleByAid(int aid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Article art = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select aid,a.uId,title,article,date,a.mid,account,"
					+ "image,m.mName,u.username from t_model m join t_article a "
					+ "on m.mid=a.mid join t_user u on a.uId=u.uId where a.aid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, aid);
			rs = ps.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setAid(rs.getInt(1));
				art.setuId(rs.getInt(2));
				art.setTitle(rs.getString(3));
				art.setArticle(rs.getString(4));
				art.setDate(rs.getDate(5));
				art.setMid(rs.getInt(6));
				art.setAccount(rs.getInt(7));
				art.setImage(rs.getString(8));
				art.setmName(rs.getString(9));
				art.setUsername(rs.getString(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return art;
	}
	
	/**
	 * ��ѯ���±����ж���������
	 * @return ������
	 */
	public int getTotalRowsFromArticle() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows = 0;
		try {
			conn = DBUtils.getConn();
			String sql = "select count(1) from t_article";
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
	
	
	/**
	 * ���ĳ�����£������µĵ�����ͻ�����1
	 * @param aid
	 */
	public void addAccound(int aid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "update t_article set account = account+1 where aid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, aid);
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
	}

}






















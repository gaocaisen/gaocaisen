package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Article;
import entity.Model;
import util.DBUtils;

public class IndexDAO {

	/**
	 * ��ҳ�в�ѯ���е�����
	 * @return ���µļ���
	 */
	public List<Article> listArticles(int page, int num) {
		List<Article> list = new ArrayList<Article>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select aid,title,image from t_article order by date desc limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page - 1) * num);
			ps.setInt(2, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				Article art = new Article();
				art.setAid(rs.getInt(1));
				art.setTitle(rs.getString(2));
				art.setImage(rs.getString(3));

				list.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
			String sql = "select title,article,date,account,username  "
					+ "from t_article a join t_user u on a.uId=u.uId where aid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, aid);
			rs = ps.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setTitle(rs.getString(1));
				art.setArticle(rs.getString(2));
				art.setDate(rs.getDate(3));
				art.setAccount(rs.getInt(4));
				art.setUsername(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return art;
	}
	
	
	
	/**
	 * ������Ŀ���Ʋ�ѯ��ǰ��Ŀ���ж�������
	 * @return ��ǰ��Ŀ�����¼���
	 */
	public List<Article> getArticlesByMid(int mid,int page,int num) {
		List<Article> list = new ArrayList<Article>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select aid,title from t_article where mid=? order by date limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
			ps.setInt(2, (page-1)*num);
			ps.setInt(3, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				Article art = new Article();
				art.setAid(rs.getInt(1));
				art.setTitle(rs.getString(2));

				list.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps, rs);
		}

		return list;
	}

	/**
	 * ��ѯ������Ŀ������
	 * @return ��Ŀ�ļ���
	 */
	public List<Model> listAllModels(int page, int num) {
		List<Model> list = new ArrayList<Model>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_model order by mid desc limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page - 1) * num);
			ps.setInt(2, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				Model model = new Model();
				model.setMid(rs.getInt(1));
				model.setmName(rs.getString(2));
				model.setsMid(rs.getInt(3));

				list.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, ps, rs);
		}

		return list;
	}
	
	/**
	 * ������Ŀid��ѯ��ǰ��Ŀ��Ϣ
	 * @param mid ��Ŀid
	 * @return ��ǰ��Ŀ��Ϣ
	 */
	public Model getModelByMid(int mid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Model model = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_model where mid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
			rs = ps.executeQuery();
			while(rs.next()) {
				model = new Model();
				model.setMid(rs.getInt(1));
				model.setmName(rs.getString(2));
				model.setsMid(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return model;
	}
	
	/**
	 * ��ѯ������Ŀ��������������
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
	 * ��ѯĳ����Ŀ�з����˶���ƪ����
	 * @return ĳ��Ŀ�������������
	 */
	public int getTotalRowsFromArticleByMid(int mid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows = 0;
		try {
			conn = DBUtils.getConn();
			String sql = "select count(1) from t_article where mid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
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

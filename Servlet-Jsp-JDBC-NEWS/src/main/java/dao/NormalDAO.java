package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Article;
import util.DBUtils;

public class NormalDAO {
	
	/**
	 * 根据用户id查询当前用户的文章
	 * @param uId 用户id
	 * @return 包含用户发布的所有文章的集合
	 */
	public List<Article> listUserArticle(int uId,int count){
		List<Article> list = new ArrayList<Article>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select aid,a.uId,title,article,date,a.mid,account,"
					+ "image,m.mName,u.username from t_model m join t_article a "
					+ "on m.mid=a.mid join t_user u on a.uId=u.uId where a.uId=? "
					+ "order by date desc limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uId);
			ps.setInt(2, (count-1)*10);
			ps.setInt(3, 10);
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
	 * 查询当前用户发表了多少文章
	 * @param uId 用户id
	 * @return 发表的文章数
	 */
	public int getTotalRowsFromNormal(int uId) {
		List<Article> list = new ArrayList<Article>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows = 0;
		try {
			conn = DBUtils.getConn();
			String sql = "select count(1) from t_article where uId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uId);
			rs = ps.executeQuery();
			while(rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return rows;
	}
	
	
}

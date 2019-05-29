package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Model;
import entity.ModelVO;
import util.DBUtils;

public class ModelDAO {
	
	/**
	 * 添加栏目
	 * @param model 栏目信息的实体类
	 * @return 增加的主键id
	 */
	public String addModel(Model model) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "insert into t_model values(null,?,?)";
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getmName());
			ps.setInt(2, model.getsMid());
			int rows = ps.executeUpdate();
			if(rows>0) {
				rs = ps.getGeneratedKeys();
				while(rs.next()) {
					//返回增加成功后的主键id
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
	 * 根据栏目id删除栏目
	 * @param mid 栏目id
	 * @return true代表删除成功，false代表删除失败
	 */
	public boolean delModel(int mid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "delete from t_model where mid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
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
	 * 根据上级栏目id删除该栏目下所有子栏目
	 * @param sMid 上级栏目id
	 * @return
	 */
	public boolean deleteAllModelsInOneLevel(int sMid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "delete from t_model where sMid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sMid);
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
	 * 修改栏目
	 * @param model 栏目信息
	 * @return true代表成功，false代表失败
	 */
	public boolean changeModel(int mid,Model model) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "update t_model set mName=?,sMid=? where mid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, model.getmName());
			ps.setInt(2, model.getsMid());
			ps.setInt(3, mid);
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
	 * 查询所有一级栏目
	 * @return 栏目的集合
	 */
	public List<Model> listLevelOneModel(){
		List<Model> list = new ArrayList<Model>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_model where sMid=? order by mid";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while(rs.next()) {
				Model model = new Model();
				model.setMid(rs.getInt(1));
				model.setmName(rs.getString(2));
				model.setsMid(rs.getInt(3));
				
				list.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return list;
	}
	
	/**
	 * 查询所有栏目
	 * @return 栏目的集合
	 */
	public List<Model> listAllModels(){
		List<Model> list = new ArrayList<Model>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_model order by mid";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Model model = new Model();
				model.setMid(rs.getInt(1));
				model.setmName(rs.getString(2));
				model.setsMid(rs.getInt(3));
				
				list.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return list;
	}
	
	
	
	/**
	 * 根据上级栏目sMid查询所有的栏目
	 * @param sMid 上级栏目id
	 * @return 同一类栏目的集合
	 */
	public List<ModelVO> getModelsBysMid(int sMid){
		List<ModelVO> list = new ArrayList<ModelVO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select t2.mid,t2.mName,t2.sMid,t1.mName smName from t_model t1 join t_model t2 on t1.mid=t2.sMid where t2.sMid=? order by t2.mid; ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sMid);
			rs = ps.executeQuery();
			while(rs.next()) {
				ModelVO modelVo = new ModelVO();
				modelVo.setMid(rs.getInt(1));
				modelVo.setmName(rs.getString(2));
				modelVo.setsMid(rs.getInt(3));
				modelVo.setSmName(rs.getString(4));
				list.add(modelVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		
		return list;
	}
	
	/**
	 * 根据栏目id查询当前栏目信息
	 * @param mid 栏目id
	 * @return 当前栏目信息
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
	

}














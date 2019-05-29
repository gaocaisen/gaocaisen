package user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import dao.UserDAO;
import entity.User;
import util.DBUtils;

public class Test1 {
	
	@Test
	public void getConn() {
		try {
			Connection conn = DBUtils.getConn();
			System.out.println(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkLogin() {
		UserDAO dao = new UserDAO();
		String username="root";
		String password = "root";
		User user = dao.checkLogin(username, password);
		System.out.println(user);
	}
	
	@Test
	public void addUser() {
		UserDAO dao = new UserDAO();
		User user = new User(null,"xiao123","123456",1,null,"");
		boolean flag = dao.addUser(user );
		System.out.println(flag);
		
	}
	
	@Test
	public void delete() {
		UserDAO dao = new UserDAO();
		boolean flag = dao.delUser(2);
		System.out.println(flag);
	}
	
	@Test
	public void changeUser() {
		UserDAO dao = new UserDAO();
		User user = new User();
		user.setUsername("xiaoyuehong");
		user.setPassword("1234");
		user.setdId(2);
		boolean flag = dao.modifyUser(4, user);
		System.out.println(flag);
	}
	
	@Test
	public void listUser() {
		UserDAO dao = new UserDAO();
		List<User> list = dao.listUser(3);
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	@Test
	public void getUserByUid() {
		UserDAO dao = new UserDAO();
		User user = dao.getUserByUid(1);
		System.out.println(user);
	}
	
	@Test
	public void getUserByDid() {
		UserDAO dao = new UserDAO();
		List<User> list = dao.getUserByDid(1);
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testUsername() {
		UserDAO dao = new UserDAO();
		String username = "root";
		boolean flag = dao.getUserByUAP(username);
		System.out.println(flag);
	}

}














package dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import dao.ConnectionPool;
import dao.UserDAO;
import entity.Role;
import entity.User;

public class UserDAOImplTest {

	
	private static UserDAO userDAO = null;
	private static User user = null;

	@BeforeClass
	public static void beforeClass() {
		userDAO = new UserDAOImpl();
		user = new User("Viktar", "Baradzin", true, Role.ADMIN, "abra@cadabra");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/adv_lib?useSSL=false");
		dataSource.setUser("bestuser");
		dataSource.setPassword("bestuser");
		new ConnectionPool(dataSource);
	}

	@Test
	public void testInsertUser() {
		userDAO.insertUser(user);
		User testUser = null;
		testUser = userDAO.getUserByUsername(user.getUsername());
		assertEquals(testUser.getUsername(), user.getUsername());
		assertEquals(testUser.getRole(), user.getRole());
		userDAO.deleteUserByid(testUser.getId());
		testUser = userDAO.getUserByUsername(user.getUsername());
		assertNull(testUser);
	}
	
}

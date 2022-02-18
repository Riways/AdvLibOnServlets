package dao;

public class Query {

	public static final String INSERT_NEW_USER = "INSERT INTO users(  username, password, enabled, role, email) VALUES(  ?, ?, ? , ?, ?);";
	public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=? ";
	public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username=?";
}

package dao;

import entity.User;

public interface UserDAO {
	
	public User getUserByUsername( String username);
	
	public void insertUser(User user);
	
	public void deleteUserByid(Integer userID);
	
	public void closeConnection();
	
}

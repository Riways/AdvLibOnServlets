package dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import dao.ConnectionPool;
import dao.Query;
import dao.UserDAO;
import entity.Role;
import entity.User;

public class UserDAOImpl implements UserDAO {

	private final static Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

	private Connection connection;

	/**
	 * Getting user by username from DB
	 */
	@Override
	public User getUserByUsername(String username) {

		connection = ConnectionPool.getConnection();

		User user = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_USER_BY_USERNAME)) {
			prepSttmnt.setString(1, username);
			
			ResultSet resSet = prepSttmnt.executeQuery();
		
			resSet.next();
			
			LOGGER.debug("user " + username + " find.");
			Role role = null;
			// getting user role
			role = resSet.getString("role").equals("client") ? Role.CLIENT : Role.ADMIN;
			user = new User(resSet.getInt(1), resSet.getString(2), resSet.getString(3), resSet.getBoolean(4), role,
					resSet.getString(6));
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}

		return user;
	}

	@Override
	public void insertUser(User user) {
		connection = ConnectionPool.getConnection();

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.INSERT_NEW_USER)) {
			prepSttmnt.setString(1, user.getUsername());
			prepSttmnt.setString(2, user.getPassword());
			prepSttmnt.setBoolean(3, user.isEnabled());
			prepSttmnt.setString(4, user.getRole().getName());
			prepSttmnt.setString(5, user.getEmail());

			prepSttmnt.executeUpdate();

			LOGGER.debug("user: " + user.getUsername() + " added");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void deleteUserByid(Integer userID) {
		connection = ConnectionPool.getConnection();

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.DELETE_USER_BY_ID)) {
			
			prepSttmnt.setInt(1, userID);
			
			prepSttmnt.executeUpdate();
			LOGGER.debug("user: " + userID + " deleted");
			
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	/**
	 * Closing connection(if exists)
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
	}

}
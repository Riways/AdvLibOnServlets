package dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import constants.Query;
import dao.ConnectionPool;
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

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_USER_BY_USERNAME)) {
			prepSttmnt.setString(1, username);

			resSet = prepSttmnt.executeQuery();

			// Checking on empty result && moving pointer on first position, if result not
			// empty
			if (!resSet.next()) {
				LOGGER.debug("user " + username + " not found.");
				return null;
			}

			LOGGER.debug("user " + username + " found.");
			Role role = null;
			// getting user role
			role = resSet.getString("role").equals("client") ? Role.CLIENT : Role.ADMIN;
			user = new User(resSet.getInt(1), resSet.getString(2), resSet.getString(3), resSet.getBoolean(4), role,
					resSet.getString(6));
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			try {
				resSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

			int affectedRows = prepSttmnt.executeUpdate();

			if(affectedRows == 0){
				throw new SQLException("User saving wasn't succesful");
			}

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

			int affectedRows = prepSttmnt.executeUpdate();

			if(affectedRows == 0){
				throw new SQLException("User deleting wasn't succesful");
			}
			LOGGER.debug("user: " + userID + " deleted");

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	/**
	 * checking is same username exists in database,
	 * @return true if it already taken by another user
	 */
	@Override
	public boolean isUsernameAlreadyTaken(String username) {
		
		if(getUserByUsername(username) == null) {
			LOGGER.debug(username + " is not taken by another user");
			return false;
		}
		
		LOGGER.debug(username + " is taken by another user, use another username");
		return true;
	}

	/**
	 * Closing connection(if exists)
	 */
	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}
}

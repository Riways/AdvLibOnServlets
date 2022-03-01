package dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import constants.Query;
import dao.AuthorDAO;
import dao.ConnectionPool;
import entity.Author;

public class AuthorDAOImpl implements AuthorDAO {

	private final static Logger LOGGER = Logger.getLogger(AuthorDAOImpl.class);

	private Connection connection;

	@Override
	public Author getAuthorByFullname(String firstName, String lastName) {

		connection = ConnectionPool.getConnection();

		Author author = null;

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_AUTHOR_BY_FULLNAME)) {

			prepSttmnt.setString(1, firstName);
			prepSttmnt.setString(2, lastName);

			resSet = prepSttmnt.executeQuery();

			// Checking on empty result && moving pointer on first position, if result not
			// empty
			if (!resSet.next()) {
				LOGGER.debug("author " + firstName + lastName + " not found.");
				return null;
			}

			LOGGER.debug("author " + firstName + lastName + " found.");
			author = new Author(resSet.getInt(1), resSet.getString(2), resSet.getString(3));

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

		return author;
	}

	@Override
	public Author getAuthorByID(int id) {
		connection = ConnectionPool.getConnection();

		Author author = null;

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_AUTHOR_BY_ID)) {

			prepSttmnt.setInt(1, id);

			resSet = prepSttmnt.executeQuery();

			// Checking on empty result && moving pointer on first position, if result not
			// empty
			if (!resSet.next()) {
				LOGGER.debug("author with id " + id + " not found.");
				return null;
			}

			LOGGER.debug("author with id " + id + " found.");

			author = new Author(resSet.getInt(1), resSet.getString(2), resSet.getString(3));

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

		return author;
	}

	@Override
	public List<Author> getAllAuthors() {

		connection = ConnectionPool.getConnection();

		List<Author> authors = new ArrayList<Author>();

		Author author = new Author();

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_ALL_AUTHORS)) {

			resSet = prepSttmnt.executeQuery();

			// Checking on empty result && moving pointer on first position, if result not
			// empty
			if (!resSet.next()) {
				LOGGER.debug("No authors found");
				return null;
			}

			do {
				author = new Author(resSet.getInt(1), resSet.getString(2), resSet.getString(3));
				authors.add(author);

			} while (resSet.next());
			LOGGER.debug("authors obtained from DB: " + authors);

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

		return authors;
	}

	/**
	 *@return author id
	 */
	@Override
	public int saveAuthor(Author authorToSave) {
		connection = ConnectionPool.getConnection();

		int savedAuthorID = 0;

		ResultSet resSet = null;

		String firstName = authorToSave.getFirstName();
		String lastName = authorToSave.getLastName();
		
		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.INSERT_NEW_AUTHOR,
				Statement.RETURN_GENERATED_KEYS)) {

			prepSttmnt.setString(1, firstName);
			prepSttmnt.setString(2,lastName);

			int affectedRows = prepSttmnt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Author saving wasn't succesful");
			}

			resSet = prepSttmnt.getGeneratedKeys();

			if (resSet.next()) {
				savedAuthorID = resSet.getInt(1);
			} else {
				throw new SQLException("Generated id for saved author wasn't found");
			}
			
			LOGGER.debug("Author " + firstName + " " + lastName + " was saved ");

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return savedAuthorID;
	}

	
	/**
	 * Checking if the saving author is in the database, otherwise inserting new author 
	 * @return author id 
	 */
	@Override
	public int saveAuthorIfNotExist(Author authorToSave) {

		int authorID = 0;

		String firstName = authorToSave.getFirstName();
		String lastName = authorToSave.getLastName();
		
		Author tempAuthor  = getAuthorByFullname( firstName, lastName);
		
		if(tempAuthor != null) {
			authorID = tempAuthor.getId();
		}else {
			authorID = saveAuthor(authorToSave);
		}
		
		return authorID;
	}

	@Override
	public void deleteAuthorByID(int id) {
		connection = ConnectionPool.getConnection();

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.DELETE_AUTHOR_BY_ID)) {

			prepSttmnt.setInt(1, id);

			int affectedRows = prepSttmnt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Author deleting wasn't succesful");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
	}

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

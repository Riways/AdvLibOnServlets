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
import dao.BookFromLibraryDAO;
import dao.ConnectionPool;
import entity.Author;
import entity.BookFromLibrary;

public class BookFromLibraryDAOImpl implements BookFromLibraryDAO {

	private final static Logger LOGGER = Logger.getLogger(BookFromLibraryDAOImpl.class);

	private Connection connection;

	@Override
	public BookFromLibrary getBookById(int id) {

		AuthorDAOImpl authorDAO = new AuthorDAOImpl();

		connection = ConnectionPool.getConnection();

		BookFromLibrary book = null;

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_BOOK_BY_ID)) {

			prepSttmnt.setInt(1, id);

			resSet = prepSttmnt.executeQuery();

			// Checking on empty result && moving pointer on first position, if result not
			// empty
			if (!resSet.next()) {
				LOGGER.debug("Book with id " + id + " not found.");
				return null;
			}

			LOGGER.debug("Book with id " + id + " found.");

			Author author = authorDAO.getAuthorByID(resSet.getInt(3));

			book = new BookFromLibrary(resSet.getInt(1), resSet.getString(2), author, resSet.getString(4),
					resSet.getString(5));

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

		return book;
	}

	/**
	 * Saves book
	 * 
	 * @return id, generated for saved book by database
	 */
	@Override
	public int saveBook(BookFromLibrary bookToSave) {

		connection = ConnectionPool.getConnection();
		ResultSet resSet = null;
		int savedBookID = 0;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.INSERT_NEW_BOOK,  Statement.RETURN_GENERATED_KEYS)) {

			prepSttmnt.setString(1, bookToSave.getBookName());
			prepSttmnt.setInt(2, bookToSave.getAuthor().getId());
			prepSttmnt.setString(3, bookToSave.getFileName());
			prepSttmnt.setString(4, bookToSave.getPathToFile());

			int affectedRows = prepSttmnt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Book saving wasn't succesful");
			}

			LOGGER.debug("user: " + bookToSave.getBookName() + " added");

			resSet = prepSttmnt.getGeneratedKeys();
			if (resSet.next()) {
				savedBookID = resSet.getInt(1);
			} else {
				throw new SQLException("Generated id for saved book wasn't found");
			}
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

		return savedBookID;

	}

	@Override
	public List<BookFromLibrary> getAllBooks() {
		connection = ConnectionPool.getConnection();

		AuthorDAOImpl authorDAO = new AuthorDAOImpl();

		List<BookFromLibrary> books = null;

		BookFromLibrary book = null;

		Author author = null;

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_ALL_BOOKS)) {

			resSet = prepSttmnt.executeQuery();

			// Checking on empty result && moving pointer on first position, if result not
			// empty
			if (!resSet.next()) {
				LOGGER.debug("No books found");
				return null;
			}

			do {
				if (books == null) {
					books = new ArrayList<BookFromLibrary>();
				}
				author = authorDAO.getAuthorByID(resSet.getInt(3));
				book = new BookFromLibrary(resSet.getInt(1), resSet.getString(2), author, resSet.getString(4),
						resSet.getString(5));
				books.add(book);
			} while (resSet.next());
			LOGGER.debug("books obtained from DB: " + books);

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

		return books;
	}

	@Override
	public void deleteBookByID(int id) {
		connection = ConnectionPool.getConnection();

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.DELETE_BOOK_BY_ID)) {

			prepSttmnt.setInt(1, id);

			int affectedRows = prepSttmnt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Book deleting wasn't succesful");
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

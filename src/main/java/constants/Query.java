package constants;

public class Query {

	/**
	 * User queries:
	 */
	public static final String INSERT_NEW_USER = "INSERT INTO users(  username, password, enabled, role, email) VALUES(  ?, ?, ? , ?, ?);";
	public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=? ";
	public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username=?";

	/**
	 * BookFromLibrary queries:
	 */
	public static final String SELECT_BOOK_BY_ID = "SELECT * FROM book_library WHERE id=?";
	public static final String INSERT_NEW_BOOK = "INSERT INTO book_library(  book_name, author_id, file_name, path_to_file) VALUES(  ?, ?, ? , ?);";
	public static final String SELECT_ALL_BOOKS = "SELECT * FROM book_library";
	public static final String DELETE_BOOK_BY_ID = "DELETE FROM book_library WHERE id=? ";

	/**
	 * Author queries:
	 */
	public static final String INSERT_NEW_AUTHOR = "INSERT INTO authors(  first_name, last_name) VALUES(  ?, ?);";
	public static final String SELECT_AUTHOR_BY_FULLNAME = "SELECT * FROM authors WHERE LOWER(first_name)=LOWER(?) AND LOWER(last_name)=LOWER(?)";
	public static final String SELECT_AUTHOR_BY_ID = "SELECT * FROM authors WHERE id=?";
	public static final String SELECT_ALL_AUTHORS = "SELECT * FROM authors";
	public static final String DELETE_AUTHOR_BY_ID = "DELETE FROM authors WHERE id=? ";
	
	/**
	 * Dictionary queries:
	 */
	public static final String SELECT_ALL_WORDS_FROM_DICTIONARY = "SELECT * FROM dictionary";
	
	/**
	 * OxfordThreeThousand queries:
	 */
	public static final String SELECT_ALL_WORDS_FROM_OXFORD_THREE_THOUSAND = "SELECT * FROM oxford3000";
}

package dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import dao.AuthorDAO;
import dao.BookFromLibraryDAO;
import dao.ConnectionPool;
import entity.Author;
import entity.BookFromLibrary;

public class BookFromLibraryDAOTest {

	private static BookFromLibraryDAO bookDAO = null;
	private static AuthorDAO authorDao = null;
	private static Author author = null;
	private static BookFromLibrary book = null;

	@BeforeClass
	public static void beforeClass() {
		bookDAO = new BookFromLibraryDAOImpl();
		authorDao = new AuthorDAOImpl();
		author = new Author("Petr", "Ivanov");
		book = new BookFromLibrary( "Kadabra", author, "test", "/a/b/c.txt");
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
		int savedAuthorID = authorDao.saveAuthorIfNotExist(author);
		author.setId(savedAuthorID);
		int savedBookID = bookDAO.saveBook(book);
		BookFromLibrary savedBook  = bookDAO.getBookById(savedBookID);
		
		assertEquals(savedBook.getBookName(), book.getBookName());
		assertEquals(savedBook.getFileName(), book.getFileName());
		assertEquals(savedBook.getPathToFile(), book.getPathToFile());
		
		List<BookFromLibrary> allBooks = bookDAO.getAllBooks();
		
		assertTrue(allBooks != null && allBooks.size() > 0);

		bookDAO.deleteBookByID(savedBookID);
	}

}

package dao;

import java.util.List;

import entity.BookFromLibrary;

public interface BookFromLibraryDAO {

	public BookFromLibrary getBookById(int id);
	
	public List<BookFromLibrary> getAllBooks();
	
	public int saveBook(BookFromLibrary bookToSave);
	
	public void deleteBookByID(int id);
	
	public void closeConnection();
}

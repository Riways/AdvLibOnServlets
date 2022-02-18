package dao;

public interface DAOFactory {

	public  AuthorDAO getAuthorDAO();
	
	public  BookFromLibraryDAO getBookFromLibraryDAO();
	
	public  UserDAO getUserDAO();
	
	public  WordFromDictionaryDAO WordFromDictionaryDAO();
	
	
	
}

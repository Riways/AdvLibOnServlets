package dao;

import java.util.List;

import entity.Author;

public interface AuthorDAO {

	public	Author getAuthorByFullname(String firstName, String lastName);

	public Author getAuthorByID(int id);
	
	public List<Author> getAllAuthors();

	public int saveAuthor(Author authorToSave);
	
	public int saveAuthorIfNotExist(Author authorToSave);
	
	public void deleteAuthorByID(int id);
	
	public void closeConnection();
	
}

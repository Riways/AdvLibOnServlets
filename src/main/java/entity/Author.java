package entity;


public class Author {

	private int id;

	private String firstName;

	private String lastName;

	/* private List<BookFromLibrary> authorBooks; */

	public Author() {
	}

	public Author(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;

	}
	
	public Author(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * public List<BookFromLibrary> getAuthorBooks() { return authorBooks; }
	 * 
	 * public void setAuthorBooks(List<BookFromLibrary> authorBooks) {
	 * this.authorBooks = authorBooks; }
	 */

	@Override
	public String toString() {
		return "Author [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	
}

package entity;


import java.util.List;

public class Author {

    private int id;

    private String fullName;

    private List<BookFromLibrary> authorBooks;

    public Author() {
    }


    public Author( String fullName) {
        this.fullName = fullName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<BookFromLibrary> getAuthorBooks() {
        return authorBooks;
    }

    public void setAuthorBooks(List<BookFromLibrary> authorBooks) {
        this.authorBooks = authorBooks;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", authorBooks=" + authorBooks +
                '}';
    }
}

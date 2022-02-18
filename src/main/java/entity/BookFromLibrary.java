package entity;


public class BookFromLibrary {

    private int id;

    private String bookName;

    private int wordsInBook;

    private Author author;

    private String fileName;

    private String pathToFile;

    public BookFromLibrary() {
    }

    public BookFromLibrary(String bookName, int wordsInBook, Author author, String fileName, String pathToFile) {
        this.bookName = bookName;
        this.wordsInBook = wordsInBook;
        this.author = author;
        this.fileName = fileName;
        this.pathToFile = pathToFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getWordsInBook() {
        return wordsInBook;
    }

    public void setWordsInBook(int wordsInBook) {
        this.wordsInBook = wordsInBook;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public String toString() {
        return "BookFromLibrary{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", wordsInBook=" + wordsInBook +
                ", author=" + author +
                ", fileName='" + fileName + '\'' +
                ", pathToFile='" + pathToFile + '\'' +
                '}';
    }
}

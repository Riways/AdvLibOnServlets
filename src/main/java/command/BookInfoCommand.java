package command;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Path;
import dao.BookFromLibraryDAO;
import dao.implementation.BookFromLibraryDAOImpl;
import entity.BookFromLibrary;
import util.file_parser.GetWordsFromFile;
import util.file_parser.GetWordsFromFileImplementaton;
import util.file_parser.WordAndCounter;
import web.ActionType;

public class BookInfoCommand implements Command {

	private String doGet(HttpServletRequest req, HttpServletResponse res) {
		
		String page = Path.BOOK_INFO_PAGE;
		
		GetWordsFromFile getWords = new GetWordsFromFileImplementaton();
		
		BookFromLibraryDAO bookDAO = new BookFromLibraryDAOImpl();
		
		String bookId = req.getParameter("bookId");
		
		BookFromLibrary book = bookDAO.getBookById(Integer.valueOf(bookId));
		
		String pathToFile = book.getPathToFile();
		String bookName = book.getBookName();
		
		File file = new File(pathToFile);
		
		ArrayList<WordAndCounter> validWordsFromFile = getWords.getValidWords(file);
		
		
		req.setAttribute("bookName" , bookName);
		req.setAttribute("validWords" , validWordsFromFile);
		
		return page;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		if(type==ActionType.GET) {
			return doGet(req, res);
		}else {
			return Path.REDIRECT_TO_WELCOME_PAGE;
		}
		
	}

}

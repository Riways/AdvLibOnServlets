package command;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

		String sortType = req.getParameter("sortBy");

		if (sortType != null) {
			sortList(validWordsFromFile, sortType);
		}

		req.setAttribute("bookName", bookName);
		req.setAttribute("validWords", validWordsFromFile);

		return page;
	}

	private void sortList(ArrayList<WordAndCounter> validWordsFromFile, String sortType) {
		if(sortType.equals("alphabet")) {
			Comparator<WordAndCounter> alphabetComparator = (a , b) -> {return a.getWord().compareTo(b.getWord());};
			Collections.sort(validWordsFromFile, alphabetComparator);
		}else if(sortType.equals("oxf")) {
			Comparator<WordAndCounter> oxfComparator = (a , b) -> {if(a.isWordInOxford3000() == b.isWordInOxford3000()) {
				return 0;}else if(a.isWordInOxford3000()) {return 1;} else {return -1;}};
			Collections.sort(validWordsFromFile,oxfComparator);
		
		}else if(sortType.equals("counter")) {
			Collections.sort(validWordsFromFile);
		}
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		if (type == ActionType.GET) {
			return doGet(req, res);
		} else {
			return Path.REDIRECT_TO_WELCOME_PAGE;
		}

	}

}

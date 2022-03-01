package command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Path;
import dao.BookFromLibraryDAO;
import dao.implementation.BookFromLibraryDAOImpl;
import entity.BookFromLibrary;
import web.ActionType;

public class WelcomeCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		
		BookFromLibraryDAO bookDAO = new BookFromLibraryDAOImpl();
		
		List<BookFromLibrary> books = bookDAO.getAllBooks();
		req.setAttribute("allBooks", books);
		req.setAttribute("counter", Integer.valueOf(1));
				
		return Path.WELCOME_PAGE;
	}

}

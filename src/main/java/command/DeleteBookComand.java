package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Path;
import dao.BookFromLibraryDAO;
import dao.implementation.BookFromLibraryDAOImpl;
import web.ActionType;

public class DeleteBookComand implements Command {

	private String doPost(HttpServletRequest req, HttpServletResponse res) {

		String page = Path.REDIRECT_TO_WELCOME_PAGE;

		BookFromLibraryDAO bookDAO = new BookFromLibraryDAOImpl();

		String bookId = req.getParameter("bookId");

		bookDAO.deleteBookByID(Integer.valueOf(bookId));

		return page;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		if (type == ActionType.GET) {
			return Path.WELCOME_PAGE;
		} else {
			return doPost(req, res);
		}
	}

}

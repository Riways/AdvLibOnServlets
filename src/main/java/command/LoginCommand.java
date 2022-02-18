package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import dao.UserDAO;
import dao.implementation.UserDAOImpl;
import entity.Role;
import entity.User;
import web.ActionType;
import web.Path;

public class LoginCommand implements Command {

	private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

	private String doPost(HttpServletRequest req, HttpServletResponse res) {

		String page = null;

		HttpSession session = req.getSession();

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		UserDAO userDao = new UserDAOImpl();

		User user = userDao.getUserByUsername(username);

		if (user == null) {
			LOGGER.debug("User not found");
			req.setAttribute("errorMessage", "Cannot find user with such username");
			page = Path.LOGIN_PAGE;
			return page;
		} else if (password != user.getPassword()) {
			LOGGER.debug("Password incorrect");
			req.setAttribute("errorMessage", "Password incorrect");
			page = Path.LOGIN_PAGE;
			return page;
		}

		page = Path.WELCOME_PAGE;
		
		Role role = user.getRole();
		LOGGER.trace("user role: " + role);

		session.setAttribute("user", user);
		LOGGER.trace("Session attribute 'user' set: " +  user);
		
		session.setAttribute("userRole", role.getName());
		LOGGER.trace("Session attribute 'userRole' set: " + role.getName());
		
		LOGGER.info("User " + user.getUsername() + " logged in as " + role.getName());
		
		return page;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {

		if (type == ActionType.GET) {
			return Path.LOGIN_PAGE;
		} else {
			return	doPost(req, res);
		}
	}

}

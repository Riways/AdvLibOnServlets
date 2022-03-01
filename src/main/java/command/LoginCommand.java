package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import constants.Path;
import dao.UserDAO;
import dao.implementation.UserDAOImpl;
import entity.Role;
import entity.User;
import web.ActionType;

public class LoginCommand implements Command {

	private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

	/**
	 * post request processing
	 */
	private String doPost(HttpServletRequest req, HttpServletResponse res) {

		String page = null;

		HttpSession session = req.getSession();

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		/* boolean rememberMe= req.getParameter(password); */


		UserDAO userDao = new UserDAOImpl();

		User user = userDao.getUserByUsername(username);

		/**
		 * Checking 'user' parameter on null && password correctness
		 * 
		 */
		if (user == null) {
			page = Path.REDIRECT_TO_LOGIN_PAGE + Path.ERROR_MESSAGE + "Cannot find user with such username";
			return page;
		} else if (!password.equals(user.getPassword())) {
			LOGGER.debug("Password incorrect");
			page = Path.REDIRECT_TO_LOGIN_PAGE + Path.ERROR_MESSAGE + "Password incorrect";
			return page;
		}

		page = Path.REDIRECT_TO_WELCOME_PAGE;

		Role role = user.getRole();
		LOGGER.trace("user role: " + role);

		session.setAttribute("user", user);
		LOGGER.trace("Session attribute 'user' set: " + user);

		session.setAttribute("userRole", role.getName());
		LOGGER.trace("Session attribute 'userRole' set: " + role.getName());
		
		/*
		 * if (req.getParameter("rememberMeCheckbox") != null) {
		 * session.setMaxInactiveInterval(604800); LOGGER.trace("Remember me == true");
		 * }else { LOGGER.trace("Remember me == false"); }
		 */
		
		LOGGER.info("User " + user.getUsername() + " logged in as " + role.getName());

		return page;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {

		if (type == ActionType.GET) {
			return Path.LOGIN_PAGE;
		} else {
			return doPost(req, res);
		}
	}

}

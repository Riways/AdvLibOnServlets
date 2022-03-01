package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Path;
import dao.UserDAO;
import dao.implementation.UserDAOImpl;
import entity.Role;
import entity.User;
import validation.RegistrationValidator;
import web.ActionType;

public class RegistrationCommand implements Command {

	
	
	/**
	 * processing post request of registration command
	 */
	private String doPost(HttpServletRequest req, HttpServletResponse res) {
		
		UserDAO userDAO = new UserDAOImpl();
		
		String page = null;
		
		/*
		 * obtaining parameters from a query
		 */
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		if(!RegistrationValidator.isValidLogin(username)) {
			page = Path.REDIRECT_TO_REGISTRATION_PAGE + Path.ERROR_MESSAGE + "Invalid login, use lowercase and uppercase latin characters, decimal numbers and symbols: _- login length: 3-15 symbols";
			return page;
		}
		
		if(!RegistrationValidator.isValidPassword(password)) {
			/*
			 * page = Path.REDIRECT_TO_REGISTRATION_PAGE + Path.ERROR_MESSAGE +
			 * "Invalid password, password should contain at least one uppercase, one lowercase latin character and one decimal. Allowed symbols: @ $ ! % * # ? & password length: 8-15  symbols"
			 * ;
			 */			
			page = Path.REDIRECT_TO_REGISTRATION_PAGE + Path.ERROR_MESSAGE + "Invalid password, password should contain at least one uppercase, one lowercase latin character and one decimal. Allowed symbols: +%40+%24+%21+%25+*+%23+%3F+%26 password length: 8-15  symbols";
			return page;
		}
		
		if(!RegistrationValidator.isValidEmail(email)) {
			page = Path.REDIRECT_TO_REGISTRATION_PAGE + Path.ERROR_MESSAGE + "Invalid email";
			return page;
		}
		
		/*
		 * checking username availability
		 */
		if(userDAO.isUsernameAlreadyTaken(username)) {
			page = Path.REDIRECT_TO_REGISTRATION_PAGE + Path.ERROR_MESSAGE + "This username already taken, please insert another username";
			return page;
		}
		
		User userToRegister= new User(username, password, true, Role.CLIENT, email);
		
		userDAO.insertUser(userToRegister);

		page = Path.REDIRECT_TO_LOGIN_PAGE + Path.SUCCESS_MESSAGE + "Registration completed";
		
		return page;
		
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		if(type == ActionType.GET) {
			return Path.REGISTRATION_PAGE;
		}else {
			return doPost(req, res);
		}
		
	}

}

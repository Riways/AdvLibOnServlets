package validation;

import org.apache.log4j.Logger;

public class RegistrationValidator {

	private static final Logger LOGGER = Logger.getLogger(RegistrationValidator.class);
	
	private static final String LOGIN_REGEX = "^[a-zA-Z0-9_-]{3,15}$";
	/**
	 * ?=.*[A-Z] means at least one character in range A-Z
	 * ?=.*[a-z] means at least one character in range a-z
	 * ?=.*\\d means at least one character in range 0-9
	 * allowed special symbols: @ $ ! % * # ? &
	 * 
	 */
	private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,15}$";
	
	/**
	 */
	private static final String EMAIL_REGEX = "^[a-zA-z0-9.-]{2,30}@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,6}$";
	
	/**
	 * Login validation
	 */
	public static boolean isValidLogin(String login) {
		if(!login.matches(LOGIN_REGEX)) {
			LOGGER.debug("login: " + login + " does not match LOGIN_REGEX");
			return false;
		}
		LOGGER.debug("login: " + login + " allowed");
		return true;
	}
	
	/**
	 * Password validation
	 */
	public static boolean isValidPassword(String password) {
		if(!password.matches(PASSWORD_REGEX)) {
			LOGGER.debug("password  does not match PASSWORD_REGEX");
			return false;
		}
		LOGGER.debug("password  allowed");
		return true;
	}
	
	/**
	 * Email validation
	 */
	public static boolean isValidEmail(String email) {
		if(!email.matches(EMAIL_REGEX)) {
			LOGGER.debug("email: " + email + " does not match EMAIL_REGEX");
			return false;
		}
		LOGGER.debug("email: " + email + " allowed");
		return true;
	}
}

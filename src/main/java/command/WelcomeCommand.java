package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.Path;

public class WelcomeCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		return Path.WELCOME_PAGE;
	}

}

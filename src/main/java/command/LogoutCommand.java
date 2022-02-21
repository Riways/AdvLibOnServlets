package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import web.ActionType;
import web.Path;

public class LogoutCommand implements Command {

	private final static Logger LOGGER = Logger.getLogger(LogoutCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		
		String page = Path.WELCOME_PAGE;
		String sessionID = req.getSession().getId();
		req.getSession().invalidate();
		LOGGER.debug("Session " + sessionID + " invalidated successfully");
		return page;
	}

}

package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Path;
import web.ActionType;

public class SuccessCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		if (type == ActionType.GET) {
			return Path.SUCCESS_PAGE;
		} else {
			return	Path.REDIRECT_TO_SUCCESS_PAGE;
		}
	}

}

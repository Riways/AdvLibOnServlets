package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.ActionType;

public interface Command {
	
	
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type);
}

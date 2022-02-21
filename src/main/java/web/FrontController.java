package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import command.Command;
import command.CommandContainer;
import listeners.ServletContextListenerImpl;

@WebServlet("/controller")
public class FrontController extends HttpServlet{

	
	private static final long serialVersionUID = 6405033785660020905L;

	
	private static final Logger LOGGER = Logger.getLogger(ServletContextListenerImpl.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, ActionType.GET);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, ActionType.POST);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp, ActionType action) throws IOException, ServletException {
		
		//get command attribute from request
		String comandFromRequest = (String)req.getParameter("command");
		LOGGER.debug("Command get: " + comandFromRequest + ", action type: " + action.name());
		
		//get command from command container
		Command commandFromContainer = CommandContainer.getCommand(comandFromRequest);
		
		//getting path
		String path = commandFromContainer.execute(req, resp, action);
		
		if(path == null) {
			LOGGER.debug("Command not found");
			resp.sendRedirect(Path.WELCOME_PAGE);
		}else {
			if(action == ActionType.GET) {
				req.getRequestDispatcher(path).forward(req, resp);
			}else if (action == ActionType.POST) {
				resp.sendRedirect(path);
			}
		}
		
	}
}

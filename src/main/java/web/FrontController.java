package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import command.Command;
import command.CommandContainer;
import constants.Path;
import entity.User;
import listeners.ServletContextListenerImpl;

@WebServlet("/controller")
public class FrontController extends HttpServlet {

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

	private void processRequest(HttpServletRequest req, HttpServletResponse resp, ActionType action)
			throws IOException, ServletException {

		User user = (User) req.getSession().getAttribute("user");
		if (user != null) {
			LOGGER.debug("--------------------new request from user: " + user.getUsername() + " his role: "
					+ user.getRole() + "--------------------");
		}else {
			LOGGER.debug("--------------------new request from non-authorized  user--------------------");
		}
		LOGGER.debug("--------------------session id: "+ req.getSession().getId() + " --------------------");
		String commandFromRequest;

		// get command parameter from request
		if (ServletFileUpload.isMultipartContent(req)) {
			commandFromRequest = "uploadBook";
		} else {
			commandFromRequest = (String) req.getParameter("command");
			LOGGER.debug("Command get: " + commandFromRequest + ", action type: " + action.name());
		}

		Command commandFromContainer = CommandContainer.getCommand(commandFromRequest);

		// getting path
		String path = commandFromContainer.execute(req, resp, action);

		if (path == null) {
			LOGGER.debug("Command not found");
			resp.sendRedirect(Path.WELCOME_PAGE);
		} else {
			if (action == ActionType.GET) {
				req.getRequestDispatcher(path).forward(req, resp);
			} else if (action == ActionType.POST) {
				resp.sendRedirect(path);
			}
		}

	}

}

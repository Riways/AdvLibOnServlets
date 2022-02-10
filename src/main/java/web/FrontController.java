package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.Command;
import command.CommandContainer;

@WebServlet("/controller")
public class FrontController extends HttpServlet{

	private static final long serialVersionUID = 6405033785660020905L;

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
		System.out.println("Command get: " + comandFromRequest);
		
		//get command from command container
		Command commandFromContainer = CommandContainer.getCommand(comandFromRequest);
		
		//getting path
		String path = commandFromContainer.execute(req, resp);
		
		if(path == null) {
			System.out.println("Command not found");
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

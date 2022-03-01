package filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import constants.RolesCommands;

@WebFilter(servletNames = "FrontController",
		initParams = {
				@WebInitParam(name = "admin", value = RolesCommands.ADMIN_COMMANDS),
				@WebInitParam(name = "client", value = RolesCommands.CLIENT_COMMANDS),
				@WebInitParam(name = "common", value = RolesCommands.COMMON_COMMANDS),
				@WebInitParam(name = "unlogged", value = RolesCommands.UNLOGGED_COMMANDS)
		})
public class AccessFilter implements Filter {
	
	private final static  Logger LOGGER = Logger.getLogger(AccessFilter.class);

	private static Map<String, List<String>> accessMap = new HashMap<String, List<String>>();
	private static List<String> commons = new ArrayList<String>();
	private static List<String> unlogged= new ArrayList<String>();
	
	/**
	 * filter intialization
	 */
	@Override
	public void init(FilterConfig conf) throws ServletException {
		LOGGER.debug("Filter initialization starts");
		// roles
		accessMap.put("admin", asList(conf.getInitParameter("admin")));
		accessMap.put("client", asList(conf.getInitParameter("client")));
		LOGGER.debug("Access map --> " + accessMap);

		// commons
		commons = asList(conf.getInitParameter("common"));
		LOGGER.debug("Common commands --> " + commons);

		// out of control
		unlogged = asList(conf.getInitParameter("unlogged"));
		LOGGER.debug("Unlogged commands --> " + unlogged);

		LOGGER.debug("Filter initialization finished");
		
	}
	
	/**
	 * filter processing
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		LOGGER.debug("Filter starts");
		
		if(accessAllowed(arg0)) {
			LOGGER.debug("Filter finished");
			arg2.doFilter(arg0, arg1);
		}else {
			String errorMessasge = "You do not have permission to access the requested resource";

			arg0.setAttribute("errorMessage", errorMessasge);
			LOGGER.debug("Set the request attribute: errorMessage --> " + errorMessasge);
			arg0.getRequestDispatcher("/index.jsp").forward(arg0, arg1);
		}
	}
	
	/**
	 * Checking the user for access to a resource
	 */
	private boolean accessAllowed(ServletRequest request) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		LOGGER.debug("Command name --> " + commandName);
		//if no command,- no access
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}
		
		//command allowed for unlogged users?
		if (unlogged.contains(commandName)) {
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		
		//if session is not created - access denied
		
		if (session == null) {
			return false;
		}
		
		String userRole = (String) session.getAttribute("userRole");
		LOGGER.debug("USER ROLE: " + userRole);
		//session created,
		if(userRole == null) {
			return false;
		}
		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}
	
	/**
	 * Cutting string on tokens divided by ' ' symbol, and put them in container 
	 * @param str string obtained from the parameter in init method
	 * @return
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}
	

}

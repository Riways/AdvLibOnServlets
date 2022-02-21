package web;

public class Path {
	
	//GET paths
	public static final String WELCOME_PAGE = "resources/jsp/index.jsp" ;
	public static final String LOGIN_PAGE = "resources/jsp/login.jsp" ;
	public static final String REGISTRATION_PAGE = "resources/jsp/registration.jsp" ;
	
	//POST paths
	public static final String REDIRECT_TO_WELCOME_PAGE = "controller";
	public static final String REDIRECT_TO_LOGIN_PAGE = "controller?command=login";
	public static final String REDIRECT_TO_REGISTRATION_PAGE = "controller?command=registration";
	
	//errorMessage
	public static final String ERROR_MESSAGE = "&errorMessage=";
	
	//successMessage
	public static final String SUCCESS_MESSAGE ="&successMessage=";
}

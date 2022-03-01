package constants;

public class Path {
	
	//GET paths
	public static final String WELCOME_PAGE = "resources/jsp/index.jsp" ;
	public static final String LOGIN_PAGE = "resources/jsp/login.jsp" ;
	public static final String REGISTRATION_PAGE = "resources/jsp/registration.jsp" ;
	public static final String UPLOAD_BOOK_PAGE = "resources/jsp/upload_book.jsp" ;
	public static final String SUCCESS_PAGE = "resources/jsp/success.jsp" ;
	public static final String BOOK_INFO_PAGE = "resources/jsp/book_info.jsp" ;
	
	//POST paths
	public static final String REDIRECT_TO_WELCOME_PAGE = "controller";
	public static final String REDIRECT_TO_LOGIN_PAGE = "controller?command=login";
	public static final String REDIRECT_TO_REGISTRATION_PAGE = "controller?command=registration";
	public static final String REDIRECT_TO_UPLOAD_BOOK_PAGE = "controller?command=uploadBook";
	public static final String REDIRECT_TO_SUCCESS_PAGE = "controller?command=success";
	
	//errorMessage
	public static final String ERROR_MESSAGE = "&errorMessage=";
	
	//successMessage
	public static final String SUCCESS_MESSAGE ="&successMessage=";
}

package command;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import constants.FileUploading;
import constants.Path;
import dao.AuthorDAO;
import dao.BookFromLibraryDAO;
import dao.implementation.AuthorDAOImpl;
import dao.implementation.BookFromLibraryDAOImpl;
import entity.Author;
import entity.BookFromLibrary;
import web.ActionType;

public class UploadBookCommand implements Command {

	private final static Logger LOGGER = Logger.getLogger(UploadBookCommand.class);

	private String doPost(HttpServletRequest req, HttpServletResponse res) {

		String page;

		String fileName = null;
		
		String filePath = null;

		LOGGER.debug("multipart file in process");

		DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setSizeThreshold(FileUploading.MEMORY_THRESHOLD);
		/*
		 * temporary storage, using while file in process
		 */
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		/*
		 * object which will represent the actual file itself.
		 */
		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setFileSizeMax(FileUploading.MAX_FILE_SIZE);
		upload.setSizeMax(FileUploading.MAX_REQUEST_SIZE);

		/*
		 * path to file storage
		 */
		String uploadPath = req.getServletContext().getRealPath("") + FileUploading.UPLOAD_DIRECTORY;

		/*
		 * create directory if it doesn't exists
		 */
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			LOGGER.debug("directory " + uploadPath + " created");
			uploadDir.mkdir();
		}

		try {
			List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(req));
			LOGGER.debug("Size of formItem " + formItems.size());
			if (formItems != null && formItems.size() > 0) {
			
				
				HashMap<String, String> reqData = new HashMap<String, String>();
				
				for (FileItem item : formItems) {
					
					//Searching for multipart file in form and mapping fields in 'reqData' object
					if (!item.isFormField()) {

						fileName = item.getName();
						
						//saving fileName for saveRequestDataInDB method
						reqData.put("fileName", fileName);
						
						if (!fileName.endsWith(".txt")) {
							page = Path.REDIRECT_TO_UPLOAD_BOOK_PAGE + Path.ERROR_MESSAGE
									+ "You can upload files with .txt suffix";
							return page;
						} else {
							LOGGER.debug("Saving book " + fileName);
							
							filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);
							item.write(storeFile);
							
							//saving filePath for saveRequestDataInDB method
							reqData.put("filePath", filePath);
						}
					}else {
						//saving fields for saveRequestDataInDB method
						reqData.put(item.getFieldName(), item.getString());
					}
				}
				saveRequestDataInDB(reqData);
			}
		} catch (FileSizeLimitExceededException ex) {
			LOGGER.debug("attempt to upload a large-sized file is rejected " );
			page = Path.REDIRECT_TO_UPLOAD_BOOK_PAGE + Path.ERROR_MESSAGE + "the maximun file size you can upload is 10mb";
			return page;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			page = Path.REDIRECT_TO_UPLOAD_BOOK_PAGE + Path.ERROR_MESSAGE + ex.getMessage();
			return page;
		}
		

		page = Path.REDIRECT_TO_SUCCESS_PAGE + Path.SUCCESS_MESSAGE + "File " + fileName
				+ " has uploaded successfully!";

		return page;

	}
	
	private void saveRequestDataInDB(HashMap<String, String> reqData) {
		
		AuthorDAO authorDAO = new AuthorDAOImpl();
		BookFromLibraryDAO bookDAO = new BookFromLibraryDAOImpl();
		
		BookFromLibrary bookToSave;
		Author authorToSave;
		
		String fileName = reqData.get("fileName");
		String filePath = reqData.get("filePath");
		String bookName = reqData.get("bookName");
		String authorsFirstName = reqData.get("authorsFirstName");
		String authorsLastName = reqData.get("authorsLastName");
		
		authorToSave = new Author(authorsFirstName, authorsLastName);
		
		//getting generated id for author 
		int auhorID = authorDAO.saveAuthorIfNotExist(authorToSave);
		authorToSave.setId(auhorID);
		
		bookToSave = new BookFromLibrary(bookName, authorToSave, fileName, filePath);
		
		bookDAO.saveBook(bookToSave);
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res, ActionType type) {
		if (type == ActionType.GET) {
			return Path.UPLOAD_BOOK_PAGE;
		} else {
			return doPost(req, res);
		}
	}

}

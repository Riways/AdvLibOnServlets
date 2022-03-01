package util.multipart;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import constants.FileUploading;

public class MultipartHandler {

	private static DiskFileItemFactory factory = new DiskFileItemFactory();

	static {
		factory.setSizeThreshold(FileUploading.MEMORY_THRESHOLD);
		/*
		 * temporary storage, using while file in process
		 */
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	}

	private final static Logger LOGGER = Logger.getLogger(MultipartHandler.class);

	/**
	 * getting specific parameter from multipart form
	 */
	/*
	 * public static String getParameterByName(String parameterName,
	 * HttpServletRequest req) { HashMap<String, String> parameters =
	 * getAllParameters(req); return parameters.get(parameterName); }
	 */

	/**
	 * Getting parameters and files from multipart form and putting them in rquest withuot enctype
	 * 
	 * @return list of parameters
	 */
	public static HashMap<String, String> parseMultipartRequest(HttpServletRequest req) {

		HashMap<String, String> parameters = new HashMap<String, String>();

		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(req));

			if (formItems != null && formItems.size() > 0) {
				/**
				 * seraching for fields in form
				 */
				for (FileItem item : formItems) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						parameters.put(name, value);
						LOGGER.debug("parameter get: " + name + " = " + value);
					}
				}
			}

			formItems = upload.parseRequest(new ServletRequestContext(req));
			System.out.println(formItems.size());
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
		}

		return parameters;
	}
	/*	*//**
			 * Getting parameters from multipart form
			 * 
			 * @return list of parameters
			 */

	/*
	 * public static HashMap<String, String> getAllParameters(HttpServletRequest
	 * req) {
	 * 
	 * HashMap<String, String> parameters = new HashMap<String, String>();
	 * 
	 * ServletFileUpload upload = new ServletFileUpload(factory);
	 * 
	 * try { List<FileItem> formItems = upload.parseRequest(new
	 * ServletRequestContext(req));
	 * 
	 * if (formItems != null && formItems.size() > 0) {
	 *//**
		 * seraching for fields in form
		 *//*
			 * for (FileItem item : formItems) { if (item.isFormField()) { String name =
			 * item.getFieldName(); String value = item.getString(); parameters.put(name,
			 * value); LOGGER.debug("parameter get: " + name + " = " + value); } } }
			 * 
			 * formItems = upload.parseRequest(new ServletRequestContext(req));
			 * System.out.println(formItems.size()); } catch (Exception ex) {
			 * ex.printStackTrace(); LOGGER.error(ex.getMessage()); }
			 * 
			 * return parameters; }
			 */
	public static DiskFileItemFactory getDiskFileItemFactory() {
		return factory;
	}

}

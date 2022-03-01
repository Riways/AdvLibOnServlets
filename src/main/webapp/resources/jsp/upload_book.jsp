<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/jspf/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload book</title>
<%@ include file="/resources/jspf/page_head.jspf"%>
</head>
<body>
	<div class="container mw-50 text-center ">


		<%@ include file="/resources/jspf/small_headline.jspf"%>

		<h3>Upload your book</h3>
		<form class="position-relative" method="POST" action="controller"
			enctype="multipart/form-data">

			<input type="hidden" name="command" value="uploadBook"> 
			<input
				type="text" class="form-control" placeholder="Book Name"
				name="bookName" required /> 
				
				<input type="text" class="form-control"
				placeholder="Author's first name" name="authorsFirstName" /> 
				
				<input
				type="text" class="form-control" placeholder="Author's last name"
				name="authorsLastName" /> 
				
				<input type="file" class="form-control"
				id="inputGroupFile02" name="uploadedBook"
				onchange="Filevalidation()" required />


			<div class="position-absolute end-0 mt-2" >
				<input type="submit" value="Save" class="input-group-text" id="saveBookButton" disabled />
			</div>
			<br> <br>
			
			<div id="sizeError"></div>
			<div>
				<c:if test="${!empty param.errorMessage}">
					<p class="alert alert-danger" role="alert">${param.errorMessage}</p>
				</c:if>
			</div>
		</form>


	</div>
	<script src="resources/static/js/upload_book.js"></script>
</body>





</html>
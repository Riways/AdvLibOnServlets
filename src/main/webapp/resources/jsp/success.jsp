<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/jspf/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title>
<%@ include file="/resources/jspf/page_head.jspf"%>
</head>
<body>
	<div class="container-xl" style="text-align: center">

		<%@ include file="/resources/jspf/small_headline.jspf"%>

		<c:if test="${!empty param.successMessage}">
			<p class="alert alert-success" role="alert">${param.successMessage}</p>
		</c:if>
		<br>
		<form action="controller">
			<input type="hidden" name="command" value="welcome">
			<button type="submit" class="btn btn-secondary">Home page</button>
		</form>
	</div>
</body>
</html>
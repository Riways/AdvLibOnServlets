<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/jspf/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
<%@ include file="/resources/jspf/page_head.jspf"%>
</head>
<body>

	<div class="container-xl" style="text-align: center">
	
		<%@ include file="/resources/jspf/small_headline.jspf"%>

		<form action="controller" method="post"
			style="max-width: 350px; margin: 0 auto" id="login-form">
			<div class="border border-secondary rounded p-3">
				<input type="hidden" name="command" value="login">
				<p>Log In</p>
				<p>
					<input type="text" name="username" class="form-control"
						placeholder="Username" required />
				</p>
				<p>
					<input type="password" name="password" class="form-control"
						placeholder="Password" required />
				</p>
<!-- 				<p>
					<input type="checkbox" name="remember-me" />&nbsp;Remember Me
				</p> -->
				<p>
					<input type="submit" value="Login" class="btn btn-primary" /> or <a
						href="controller?command=registration">Register</a>
				</p>
				
				<c:if test="${!empty param.errorMessage}">
					<p class="alert alert-danger" role="alert">Wrong user or
						password</p>
				</c:if>
				<c:if test="${!empty param.successMessage}">
					<p class="alert alert-success" role="alert">${param.successMessage}</p>
				</c:if>
				

			</div>
			
		</form>
		
	</div>
</body>
</html>
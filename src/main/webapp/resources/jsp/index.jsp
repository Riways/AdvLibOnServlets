<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/jspf/taglib.jspf"%>


<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<meta http-equiv="cache-control" content="no-cache" />
<%@ include file="/resources/jspf/page_head.jspf"%>
</head>
<body>

	<div class="container-sm text-center">

		<%@ include file="/resources/jspf/navigation.jspf"%>

		<h3 class="mt-5">Uploaded books</h3>
		
		<table class="table">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Book bookName</th>
					<th scope="col">Author</th>
					<th scope="col">Info</th>
					<th scope="col">Delete book</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${allBooks }" var="book">
					<tr>
						<th>${allBooks.indexOf(book)}</th>
						<td>${book.bookName}</td>
						<td>${book.author.firstName}  ${book.author.lastName}</td>
						<td><c:choose>
								<c:when test="${!empty userRole}">
									<a href="controller?command=bookInfo&bookId=${book.id}">Get
										info</a>
								</c:when>
								<c:otherwise>Please log in</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${userRole == 'admin'}">
									<form action="controller?command=deleteBook&bookId=${book.id}"
										method="post">
										<input class="btn btn-secondary" type="submit"
											value="delete book">
									</form>
								</c:when>
								<c:otherwise>Admin only</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>

	<%@ include file="/resources/jspf/bootstrap_scripts.jspf"%>

</body>
</html>
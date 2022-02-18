<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/jspf/taglib.jspf"%>


<!DOCTYPE html>
<html>
<head>
<title>Home</title>
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
			<%-- <tbody>
				<tr th:each="book, iter:${books}">
					<th scope="row" th:utext="${iter.count}">...</th>
					<td th:text="${book.bookName}">...</td>
					<td th:text="${book.author.fullName}">...</td>
					<td sec:authorize="isAuthenticated()"><a
						th:href="@{/bookInfo/{id}(id=${book.id})}">Info</a></td>
					<td sec:authorize="!isAuthenticated()">For authorized users</td>
					<td style="text-align: center"><a
						th:href="@{/delete/{id}(id=${book.id})}">Delete</a></td>
				</tr>
			</tbody> --%>
		</table>
	</div>

<%-- 	<c:choose>
		<c:when test="${empty userRole}">
			<form action="controller">
				<c:set var="userRole" scope="session" value="client"></c:set>
				<input type="submit" value="Become client">
			</form>
		</c:when>
		<c:otherwise>
			<form action="controller">
				<c:set var="userRole" scope="session" value=""></c:set>
				<input type="submit" value="unlog">
			</form>
		</c:otherwise>
	</c:choose> --%>


</body>
</html>
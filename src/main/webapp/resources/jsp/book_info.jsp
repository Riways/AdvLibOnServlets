<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resources/jspf/taglib.jspf"%>


<!DOCTYPE html>
<html>
<head>
<title>Book info</title>
<%@ include file="/resources/jspf/page_head.jspf"%>
</head>
<body>

	<div class="container-sm text-center">

		<%@ include file="/resources/jspf/navigation.jspf"%>


		<h3>
			Book: <span>${bookName}</span>
		</h3>
		
		
		
		<div class="dropdown position-absolute start-20">
			<button class="btn btn-secondary dropdown-toggle" type="button"
				id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false">Sort by</button>
			<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				<a class="dropdown-item" href="controller?command=bookInfo&bookId=${param.bookId}&sortBy=alphabet">Alphabet</a> <a
					class="dropdown-item" href="controller?command=bookInfo&bookId=${param.bookId}&sortBy=oxf">Not available in oxford3000</a> <a
					class="dropdown-item" href="controller?command=bookInfo&bookId=${param.bookId}&sortBy=counter">Counter</a>
			</div>
		</div>
		<br>
		<br>
		
		<div style="height: 600px; overflow: auto">



			<table class="table" style="height: 601px">
				<thead>
					<tr>
						<th scope="col">Word</th>
						<th scope="col">Counter</th>
						<th scope="col">Word in oxford3000</th>
						<th scope="col">Oxford dictionary</th>
					</tr>
				</thead>
				<tbody style="height: 601px">
					<c:forEach items="${validWords }" var="word">
						<tr>
							<td>${word.word}</td>
							<td>${word.counter}</td>
							<td><c:choose>
									<c:when test="${word.isWordInOxford3000()}">
										<div class="mx-auto" id="status-found"></div>
									</c:when>
									<c:otherwise>
										<div class="mx-auto" id="status-not-found"></div>
									</c:otherwise>
								</c:choose></td>
							<td><a
								href="https://www.oxfordlearnersdictionaries.com/definition/english/${word.word}">Search</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
	<%@ include file="/resources/jspf/bootstrap_scripts.jspf"%>
</body>
</html>




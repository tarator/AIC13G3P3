<%@page contentType="text/html" pageEncoding="UTF-8"%><!doctype html>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Queries</title>
    </head>
    <body>      
        <c:choose>
        	<c:when test="${not empty error }">
            	<h3>Error occured: <c:out value="${error}"></c:out></h3>
            </c:when>       
        	<c:otherwise>
				<p>Queries for Company <strong><c:out value="${company.name}" /></strong>.</p>
				<table border="1">
					<tr>
		        		<th>Date from</th><th>Date to</th><th>Processing/Processed</th><th>Tweet Count</th><th>result</th>
		        	</tr>
					<c:forEach items="${queries}" var="query">
						<tr>
							<td>
								<c:out value="${query.from}"></c:out>
							</td>
							<td>
								<c:out value="${query.to}"></c:out>
							</td>
							<td>
								<c:out value="${query.processed}"></c:out>
							</td>
							<td>
								<c:choose>
									<c:when test="${not empty query.result }"><c:out value="${query.result.numberOfTweets }" /></c:when>
									<c:otherwise>N/A</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${not empty query.result }"><c:out value="${query.result.sentimentValue }" /></c:when>
									<c:otherwise>N/A</c:otherwise>
								</c:choose>
							</td>
						</tr>
					
					</c:forEach>
				</table>
        	</c:otherwise>
 		</c:choose>
    </body>
    <a href='<c:url value="/" />'>Back to start page.</a>
</html>
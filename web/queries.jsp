<%@page contentType="text/html" pageEncoding="UTF-8"%><!doctype html>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        		<h3>Create new query for company <strong><c:out value="${company.name}" /></strong></h3>
                <form action="" method="POST">
                    <label>Date From:</label>
                    <fmt:formatDate value="${newQueryFrom}" pattern="dd.MM.yyyy" var="newQueryFrom" />
                    <input type="text" name="queryFrom" value="${newQueryFrom}"/>
                    <label>Date To:</label>
                    <fmt:formatDate value="${newQueryTo}" pattern="dd.MM.yyyy" var="newQueryTo" />
                    <input type="text" name="queryTo" value="${newQueryTo}" />
                    <input type="submit" value="Create Query" />
                </form>
        	
				<h3>Queries for Company <strong><c:out value="${company.name}" /></strong>.</h3>
				<table border="1" cellpadding="5">
					<tr>
		        		<th>id</th><th>Date from</th><th>Date to</th><th>Processed</th><th>Tweet Count</th><th>result</th>
		        	</tr>
					<c:forEach items="${queries}" var="query">
						<tr>
							<td>
								<c:out value="${query.idLong}"></c:out>
							</td>
							<td>
                                <fmt:formatDate value="${query.from}" pattern="dd.MM.yyyy" />
							</td>
							<td>
                                <fmt:formatDate value="${query.to}" pattern="dd.MM.yyyy" />
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
        <p>
            <a href='<c:url value="/" />'>Back to start page.</a>&nbsp;|&nbsp;<a href='<c:url value="/q/${company.name }/" />'>Reload page.</a>
        </p>
    </body>
    
</html>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!doctype html>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sentiment Analysis</title>
    </head>
    <body>
    	<c:if test="${not empty error }">
            	<h3>Error occured: <c:out value="${error }"></c:out></h3>
        </c:if> 
        <c:if test="${not empty message }">
            	<h3><c:out value="${message }"></c:out></h3>
        </c:if>  
    	<h3>Register new Company:</h3>
        <form action="/" method="POST">
            <label>CompanyName:</label>
            <input type="text" name="companyName" />
            <input type="submit" value="Register Company" />
        </form>
        
        <h3>List all registered companies:</h3>
        <table border="1" cellpadding="5">
        	<tr><th>Name</th><th>Creation date</th></tr>
	        <c:forEach items="${companies}" var="comp">
	        	<tr>
	        		<td><a href='<c:url value="/q/${comp.name}/" />'><c:out value="${comp.name}" /></a></td>
	        		<td><fmt:formatDate value="${comp.creationDate}" pattern="dd.MM.yyyy" /></td>
	        	</tr>
	        </c:forEach>
        </table>

 		<h3>Create random queries:</h3>
        <form action="/" method="POST">
            <label>Number of queries to create:</label>
            <input type="text" name="queryCount" />
            <input type="submit" value="Create queries" />
        </form>
        <p>
            <a href='<c:url value="/" />'>Reload page.</a>
        </p>
    </body>
</html>
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
			<c:when test="${not empty message }">
            	<h3><c:out value="${message}"></c:out></h3>
            </c:when>
            <c:otherwise>
            	<h3>Not sure if query was created.</h3>
            </c:otherwise>
 		</c:choose>
    </body>
    <a href='<c:url value="/" />'>Back to main-page.</a>
</html>
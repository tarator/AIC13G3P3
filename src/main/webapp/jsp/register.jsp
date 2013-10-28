<%@page contentType="text/html" pageEncoding="UTF-8"%><!doctype html>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>      
        <c:choose>
        	<c:when test="${not empty error }">
            	<h3>Registering of company with name <c:out value="${company.name}"> was not succesful: </c:out><c:out value="${error }"></c:out></h3>
            </c:when>       
        	<c:otherwise>
				<p>Company <strong><c:out value="${company.name }" /></strong> has been succesfully registered.</p>
        	</c:otherwise>
 		</c:choose>
    </body>
    <a href='<c:url value="/" />'>Back to start page.</a>
</html>
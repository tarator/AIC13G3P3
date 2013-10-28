<%@page contentType="text/html" pageEncoding="UTF-8"%><!doctype html>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    	<h3>Register new Company:</h3>
       	<form:form id="registerCompanyForm" modelAttribute="company" action="registerCompany" method="POST" >
			<form:label path="name">CompanyName:</form:label>
			<form:input path="name"/>
			<form:label path="password">Password:</form:label>
			<form:password path="password"/>       	
       		<button class="btn btn-primary" id="registerCompanySubmitButton" type="submit">Register Company</button>
       	</form:form>
        
        <h3>List all registered companies:</h3>
        <table>
        	<tr><th>Name</th><th>Creation date</th></tr>
	        <c:forEach items="${companies}" var="comp">
	        	<tr>
	        		<td><c:out value="${comp.name}" /></td>
	        		<td><c:out value="${comp.creationDate}" /></td>
	        	</tr>
	        </c:forEach>
        
        </table>
        
 
    </body>
</html>
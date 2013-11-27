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
    	<c:if test="${not empty error }">
            	<h3>Error occured: <c:out value="${error }"></c:out></h3>
        </c:if> 
        <c:if test="${not empty message }">
            	<h3><c:out value="${message }"></c:out></h3>
        </c:if>  
    	<h3>Register new Company:</h3>
       	<form:form id="registerCompanyForm" modelAttribute="company" action="registerCompany" method="POST" >
			<form:label path="name">CompanyName:</form:label>
			<form:input path="name"/>     	
       		<button class="btn btn-primary" id="registerCompanySubmitButton" type="submit">Register Company</button>
       	</form:form>
        
        <h3>List all registered companies:</h3>
        <table border="1">
        	<tr><th>Name</th><th>Creation date</th></tr>
	        <c:forEach items="${companies}" var="comp">
	        	<tr>
	        		<td><a href='<c:url value="/${comp.name}/" />'><c:out value="${comp.name}" /></a></td>
	        		<td><c:out value="${comp.creationDate}" /></td>
	        	</tr>
	        </c:forEach>
        
        </table>

        
 		<h3>Create random queries:</h3>
 		<form:form id="createRandom" modelAttribute="randomQueries" action="createRandomQueries" method="POST" >
			<form:label path="count">Number of queries to create:</form:label>
			<form:input path="count"/>     	
       		<button class="btn btn-primary" id="registerCompanySubmitButton" type="submit">Create queries</button>
       	</form:form>
       	
       	 <a href='<c:url value="/" />'>Reload page.</a>
 
    </body>
</html>
<%@ page contentType="text/html; charset=UTF-8"%>


<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>


<jsp:include page="_header.jsp"></jsp:include>

<p>Ein 'Melder'-Bean mit dem Namen '${melder.appName}' wurde in der Datenbank angelegt.<br/>
Increase the access number of this 'Melder'-Bean by calling this URL:<br />
<a href='<c:url value="/${melder.appName}/ping" />' ><c:url value="/${melder.appName}/ping" /></a>
</p>

<jsp:include page="_footer.jsp"></jsp:include>
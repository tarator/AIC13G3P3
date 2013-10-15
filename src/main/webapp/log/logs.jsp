<%@ page contentType="text/html; charset=UTF-8"%>



<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<jsp:include page="../_header.jsp"></jsp:include>
<H1>Hello!</H1>
<p>
<c:forEach items="${logEntries}" var="logEntry">
${logEntry.id } : ${logEntry.melder } , Timestamp: ${logEntry.ts } , IP: ${logEntry.ip }<br />
</c:forEach>
</p>
<jsp:include page="../_footer.jsp"></jsp:include>
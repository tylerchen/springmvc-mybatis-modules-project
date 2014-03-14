<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<h1 id="banner">Unauthorized Access !!</h1>
<hr />
<c:if test="${not empty error}">
	<div style="color: red">Your fake login attempt was bursted, dare
	again !!<br />
	Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
</c:if>

<p class="message">Access denied!</p>
<a href="${pageContext.request.contextPath}/login">Go back to login page</a>
</body>
</html>
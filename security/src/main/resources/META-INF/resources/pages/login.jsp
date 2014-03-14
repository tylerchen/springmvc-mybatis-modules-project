<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<h1>Hello Login ${hello}</h1>
<h1>${pageContext.request.contextPath}</h1>
<h1 id="banner">Login to Security Demo</h1>
<form name="f" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
<table>
	<tr>
		<td>Username:</td>
		<td><input type='text' name='j_username' /></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type='password' name='j_password'></td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan='2'><input name="submit" type="submit">&nbsp;<input
			name="reset" type="reset"></td>
	</tr>
</table>
</form>
</body>
</html>
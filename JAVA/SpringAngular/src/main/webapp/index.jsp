<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%
String getUserPrincipal = request.getUserPrincipal() != null ?  request.getUserPrincipal().getName() : "No AD";
String getRemoteUser = request.getRemoteUser();
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Index---Test
Hi ${_LOGIN_USER_.name}<br>
getUserPrincipal: ${pageContext.request.userPrincipal.name}<br>
getRemoteUser:    ${pageContext.request.remoteUser}<br>
---------------------------------------<br>
getUserPrincipal2:<%=getUserPrincipal != null ? getUserPrincipal : "No" %><br>
getRemoteUser2:<%=getRemoteUser %>
</body>
</html>
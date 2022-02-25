<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="meta.jsp"%>
<%@ include file="js_css.jsp"%>
<title><tiles:getAsString name="title"/></title>
<tiles:insertAttribute name="head" ignore="true" />
</head>
<body>
<!--這裡把 header , body, foot 組合起來-->
<!-- Header -->
<tiles:insertAttribute name="header" />
<!-- Body (Content) Page -->
<tiles:insertAttribute name="content" />
<!-- foot Page -->
<tiles:insertAttribute name="footer" ignore="true"/>
</body>
</html>
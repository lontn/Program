<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<script src="${contextPath}/js/angularjs-1.3.8/angular.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Angular here -1</title>
</head>
<body>
<div class="well span6" style="margin-top:20px;">
    <input type="text" ng-model="name" placeholder="請輸入姓名" />
    
    <div>
      <b>ngBind="name"：</b>
      <span ngBind="name"></span>
    </div>
    <div>
      <b>ng-bind="name"：</b>
      <span ng-bind="name"></span>
    </div>
    <div>
      <b>ng:bind="name"：</b>
      <span ng:bind="name"></span>
    </div>
    <div>
      <b>ng_bind="name"：</b>
      <span ng_bind="name"></span>
    </div>
    <div>
      <b>x-ng-bind="name"：</b>
      <span x-ng-bind="name"></span>
    </div>
    <div>
      <b>data-ng-bind="name"：</b>
      <span data-ng-bind="name"></span>
    </div>
  </div>
</body>
</html>
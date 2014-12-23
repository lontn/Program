<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<script src="${contextPath}/js/angularjs/angular.min.js"></script>
<script src="${contextPath}/js/controller.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DEMO</title>
</head>
<body>
Hi ${_LOGIN_USER_.name}
<label>Name:</label>
      <input type="text" ng-model="yourName" placeholder="Enter a name here">
      <hr>
      <h1>Hello {{yourName}}!</h1>
      <h1>Hello {{yourName || "AngularJS" }}!</h1>
      <h1>Hello <span ng-bind="yourName || 'AngularJS'">AngularJS</span>!!</h1>
<hr>
<form class="well span6" style="margin-top:20px;" ng-submit="submit()" ng-controller="Ctrl">
  <label>請輸入你會的技能:</label>
  <input type="text" ng-model="skill" name="skill" />
 
  <input type="submit" id="submit" value="加入" />
  <div>技能 = {{ skills }} </div>
</form>
<br/>
<hr/>
<div ng-controller="MySelectCtrl">
<b>基本下拉選單</b>：<code>label</code> <strong><code>for</code></strong> <code>value</code> <strong><code>in</code></strong> <code>array</code>
  <br>
  <select ng-model="Select1" ng-options="m.ProductName for m in Model">
    <option value="">-- 請選擇 --</option>
  </select>
  
  <hr/>
  
  <b>自訂下拉選單顯示名稱</b>：<code>label</code> <strong><code>for</code></strong> <code>value</code> <strong><code>in</code></strong> <code>array</code>
  <br>
  <select ng-model="Select2" ng-options="(m.ProductColor + ' - ' + m.ProductName) for m in Model">
    <option value="">-- 請選擇 --</option>
  </select>
  
  <hr/>
  
  <b>使用 &lt;optgroup&gt; 依據特定欄位群組下拉選單，並自訂顯示名稱</b>：<code>label</code>  <strong><code>group by</code></strong> <code>group</code> <strong><code>for</code></strong> <code>value</code> <strong><code>in</code></strong> <code>array</code>
  <br>
  <select ng-model="Select3" ng-options="(m.ProductColor + ' - ' + m.ProductName) group by m.MainCategory for m in Model">
    <option value="">-- 請選擇 --</option>
  </select>
  
  <hr/>
  
  <b>自訂 select 的 ngModel 的值：</b><code>select</code> <strong><code>as</code></strong> <code>label</code> <strong><code>for</code></strong> <code>value</code> <strong><code>in</code></strong> <code>array</code>
  <br/>
  <select ng-model="Select4" ng-options="m.id as m.ProductName for m in Model">
    <option value="">-- 請選擇 --</option>
  </select>
   
  <hr/>
  
  <b>自訂 select 的 ngModel 的值，並且自訂顯示名稱：</b><code>select</code> <strong><code>as</code></strong> <code>label</code> <strong><code>for</code></strong> <code>value</code> <strong><code>in</code></strong> <code>array</code>
  <br/>
  <select ng-model="Select5" ng-options="m.id as (m.ProductColor + ' - ' + m.ProductName) for m in Model">
    <option value="">-- 請選擇 --</option>
  </select>
   
  <hr/>
  
  <b>自訂 select 的 ngModel 的值，使用 &lt;optgroup&gt; 依據特定欄位群組下拉選單，並自訂顯示名稱：</b><code>select</code> <strong><code>as</code></strong> <code>label</code> <strong><code>for</code></strong> <code>value</code> <strong><code>in</code></strong> <code>array</code>
  <br/>
  <select ng-model="Select6" ng-options="m.id as (m.ProductColor + ' - ' + m.ProductName) group by m.MainCategory for m in Model">
    <option value="">-- 請選擇 --</option>
  </select>
   
  <hr/><br>
  
    數量: <input type="number" ng-model="quantity" ng-init="quantity = 1">
  <br>
  單價: <input type="number" ng-model="price" ng-init="price = 299">
  <br>
  總價: <span ng-bind="quantity * price"></span>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="MyApp">
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="${contextPath}/js/angularjs-1.3.8/angular.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
    var myApp = angular.module('MyApp', []);
    myApp.controller('CtrlTool', ['$scope','$http', function($scope, $http){
        $scope.friends = [ { name: '男丁格爾', age: 18 }, 
                       { name: 'jelly', age: 16 }, 
                    { name: '梅干桑', age: 30 }, 
                    { name: '莫希爾', age: 31 } ];
    }]);
</script>
<title>Angular here -1</title>
</head>
<body>
  <div class="well span4" style="margin-top: 20px;" ng-init="myStyle={}">
    <input type="button" class="btn btn-primary" value="Set" ng-click="myStyle={color:'red', 'font-size':'24px', 'background-color':'yellow'}">
    <input type="button" class="btn btn-warning" value="Clear" ng-click="myStyle={}">
    <br><br>
    <div ng-style="myStyle">男丁格爾's 脫殼玩</div>
    <pre>myStyle = {{ myStyle }}</pre>
  </div>
</body>
</html>
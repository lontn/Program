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
        $scope.skills = [];

        $scope.skill = '';
        $scope.name = '';
        $scope.submit = function() {
            console.log("name:", $scope.name);
            if (!!$scope.skill) {
                $scope.skills.push($scope.skill);
            }
            //$scope.name = $scope.name;
            $scope.skill = '';
        };
    }]);
</script>
<title>Angular here -1</title>
</head>
<body>
  <form class="well span6" style="margin-top: 20px;" ng-submit="submit()" ng-controller="CtrlTool">
    <label>姓名:</label> <input type="text" ng-model="name" name="name" placeholder="請輸入姓名"/>
    <br><label>product:</label> <input type="text" ng-model="product" name="product" ng-list/>  
    <br><label>技能:</label> <input type="text" ng-model="skill" name="skill" placeholder="請輸入"/> 
    <input type="submit" id="submit" value="加入" />
    <div>姓名 = {{ name }}</div>
    <div>product名 = {{ product }}</div>
    <div>技能 = {{ skills }}</div>
    
  </form>
</body>
</html>
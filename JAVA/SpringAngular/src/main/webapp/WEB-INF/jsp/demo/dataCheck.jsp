<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib  uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xml:lang="en-us" lang="en-us" dir="ltr" xmlns="http://www.w3.org/1999/xhtml" ng-app="VerifyData">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="${contextPath}/css/theme.css" charset="utf-8" />
<link type="text/css" rel="stylesheet" href="${contextPath}/css/styles.css" charset="utf-8" />
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.22/angular.min.js"></script>
<script>
var verifyData = angular.module('VerifyData', []);
verifyData.controller('VerifyDataController', function($scope, $http, $location, $window){
    $scope.submit = function(){
        if(!$scope.fileName){
            alert("檔名不能為空");
            return;
        }
        if(!$scope.sql){
            alert("SQL不能為空");
            return;
        }
        if(!$scope.subject){
            alert("信件標題不能為空");
            return;
        }
        if(!$scope.content){
            alert("信件的錯誤訊息不能為空");
            return;
        }
        if(!$scope.validValue){
            alert("驗證的值不能為空");
            return;
        }
        if(!$scope.mailList){
            alert("收件者mail不能為空");
            return;
        }
//         $location.path('/save');
//         $window.location.href = "${contextPath}/dataCheck/save";
      if(confirm("Are You Create DataCheck Json file?")){
        var data ={fileName : $scope.fileName,
                   SQL : $scope.sql,
                   ValidType : "euqal",
                   ValidValue : $scope.validValue,
                   InvalidError: $scope.content,
                   Title: $scope.subject,
                   mailList: $scope.mailList.split(",")
                };
        var responsePromise = $http({
            method: "POST",
            url: "${contextPath}/dataCheck/save",
            data: data
        });
        responsePromise.success(function(data, status, headers, config){
//             alert("status:"+status);
//             alert("headers:"+headers);
//             alert("config:"+config);
            $window.location.reload();//Reload
        });
        responsePromise.error(function(data, status, headers, config) {
            alert("AJAX failed!");
        });
      };
    }
});
</script>
<title>Data Check</title>
</head>

<body>
<div class="header">
<a href="${contextPath}"><img src="${contextPath}/img/logo.gif" /></a>
</div>
<div class="content" style="padding: 1em;">
  <h1 class="content-title">Data Check</h1>
</div>
<form  ng-controller="VerifyDataController">
檔名 : <input type="text" name="fileName" ng-model="fileName"></input> Ex:xxx.json(一定要json副檔名)<br/>
SQL : <textarea type="text" name="sql" ng-model="sql" rows="10" cols="50"></textarea><br/>
信件標題 : <input type="text" name="subject" ng-model="subject"></input><br/>
信件的錯誤訊息 : <input type="text" name="content" ng-model="content"></input><br/>
驗證的值: <input type="number" name="validValue" ng-model="validValue"></input><br/>
收件者mail: <input type="text" name="mailList" ng-model="mailList"></input> EX:xxx@newegg.com,yyy@newegg.com<br/>
<input type="hidden" name="validType" ng-value="euqal"></input>
<input type="button" id="submit" value="Save" ng-click="submit()"/>
<input type="reset" value="Reset" />
<hr/>
檔名 : <span ng-bind="fileName"></span><br/><br/>
SQL : <span ng-bind="sql"></span><br/><br/>
信件標題 : <span ng-bind="subject"></span><br/><br/>
信件的錯誤訊息 : <span ng-bind="content"></span><br/><br/>
驗證的值: <span ng-bind="validValue"></span><br/><br/>
收件者mail: <span ng-bind="mailList"></span><br/>
</form>
<br/>
<hr/>
<table border=1>
  <tr>
    <th>檔名</th>
    <th>信件標題</th>
    <th>信件內容</th>
    <th>收件者</th>
    <th>SQL語法</th>
  </tr>
  <c:forEach var="_file" items="${fileList}">
      <tr>
        <td><c:out value="${_file.getFileName() }"></c:out></td>
        <td><c:out value="${_file.getSubject() }"></c:out></td>
        <td><c:out value="${_file.getContent() }"></c:out></td>
        <td><c:out value="${_file.getMailList() }"></c:out></td>
        <td><c:out value="${_file.getSql() }"></c:out></td>
      </tr>
  </c:forEach>
</table>

</body>
</html>
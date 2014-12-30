<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextPath}/css/styles/kendo.common.min.css" />
    <!-- CSS -->
    <link rel="stylesheet" href="${contextPath}/css/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="${contextPath}/css/styles/kendo.dataviz.min.css" />
    <link rel="stylesheet" href="${contextPath}/css/styles/kendo.dataviz.default.min.css" />
    <!-- script -->
    <script src="${contextPath}/js/kendo/jquery.min.js"></script>
    <script src="${contextPath}/js/kendo/kendo.all.min.js"></script>
</head>
<script>
   //$(document).ready = $(function (){})
   $(function () {
       $("#grid").kendoGrid({
           dataSource: {
               type: "odata",
               transport: {
                   read: "http://demos.telerik.com/kendo-ui/service/Northwind.svc/Customers"
               },
               pageSize: 20 //每頁show 20筆
           },
           height: 550,
           groupable: true,
           sortable: true, //是否排序
           pageable: { //是否顯示分頁
               refresh: true,
               pageSizes: true, //是否要下拉分頁
               buttonCount: 5 //展示幾個分頁
           },
           columns: [{
               field: "ContactName",
               title: "Contact Name",
               width: 200
           }, {
               field: "ContactTitle",
               title: "Contact Title"
           }, {
               field: "CompanyName",
               title: "Company Name"
           }, {
               field: "Country",
               width: 150
           }]
       });
   });
</script>
<body>
<div id="example">
  <div id="grid"></div>
</div>
</body>
</html>
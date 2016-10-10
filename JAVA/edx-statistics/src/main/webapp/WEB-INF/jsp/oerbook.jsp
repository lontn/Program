<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<tiles:insertDefinition name="classic">
<tiles:putAttribute name="title">OERBook</tiles:putAttribute>
<tiles:putAttribute name="head">
<script type="text/javascript">
$(function(){                   // Start when document ready
});
</script>
</tiles:putAttribute>
<tiles:putAttribute name="content">
<div class="container">
      <div class="starter-template">
        <h1>OERBook</h1>
        <select class="selectpicker">
          <option>--Please option--</option>
          <option>Applied Science</option>
          <option>Business and Communication</option>
          <option>Social Science</option>
        </select>
        <select class="selectpicker">
          <option>--Please option--</option>
          <option>Computer Science</option>
          <option>Engineering</option>
          <option>Architecture and Design</option>
          <option>Finance</option>
        </select>
        <input id="input-3" name="input-3">
        <button>Search</button>
      </div>
</div>
</tiles:putAttribute>
</tiles:insertDefinition>

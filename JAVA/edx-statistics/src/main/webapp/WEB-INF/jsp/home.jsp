<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<tiles:insertDefinition name="classic">
<tiles:putAttribute name="title">Home</tiles:putAttribute>
<tiles:putAttribute name="head">
<script type="text/javascript">
$(function(){                   // Start when document ready

});
// $(document).on('ready', function(){
//     $('#input-3').rating({displayOnly: true, step: 1});
// });
</script>
</tiles:putAttribute>
<tiles:putAttribute name="content">
<div class="container">
      <div>
        <h1>OER目前資料數</h1>
      </div>
        <table class="table table-hover table-bordered">
          <thead>
            <tr class="active">
              <th>類別</th>
              <th>數量</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>自然科學</td>
              <td>180</td>
            </tr>
            <tr>
              <td>社會科學</td>
              <td>10</td>
            </tr>
            <tr>
              <td>人文類別</td>
              <td>20</td>
            </tr>
            <tr>
              <td>醫學類別</td>
              <td>0</td>
            </tr>
          </tbody>
        </table>
<!--         <div id="star-rating"> -->
<!--           <input type="radio" name="example" class="rating" value="1" /> <input -->
<!--             type="radio" name="example" class="rating" value="2" /> <input -->
<!--             type="radio" name="example" class="rating" value="3" /> <input -->
<!--             type="radio" name="example" class="rating" value="4" /> <input -->
<!--             type="radio" name="example" class="rating" value="5" /> -->
<!--         </div> -->
<!--         <input id="input-3" name="input-3" value="2" class="rating-loading"> -->
</div>
  </tiles:putAttribute>
</tiles:insertDefinition>

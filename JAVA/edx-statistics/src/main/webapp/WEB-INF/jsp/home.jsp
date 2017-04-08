<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<tiles:insertDefinition name="classic">
<tiles:putAttribute name="title">Home</tiles:putAttribute>
<tiles:putAttribute name="head">
<script src="${contextPath}/js/home/homeController.js"></script>
<style>
.draggable {
  float: left;
  margin: 10px 10px 10px 0;
  border: 1px solid #5BC0DE;
}

.draggable-header {
  border: 1px solid #5BC0DE;
  background: #5BC0DE;
  color: #F5F5F5;
  font-weight: bold;
  text-align: center;
}

.draggable-content {
  padding: 0.4em;
}

#sortable {
  list-style-type: none;
/*   margin-left: 2em; */
  padding: 0;
  width: 1600px;
}
</style>
</tiles:putAttribute>
<tiles:putAttribute name="content">
<div class="container">
<div class="starter-template">
  <h1>圖表</h1>
</div>
<div id="sortable">
   <div class="well">
    <form class="form-inline">
      <div class="form-group">
        <label for="sel1">年</label>
        <select class="form-control" id="year">
          <option>2015</option>
          <option>2014</option>
        </select>
        <label for="sel2">月</label>
        <select class="form-control" id="month">
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
          <option>6</option>
          <option>7</option>
          <option>8</option>
          <option>9</option>
          <option>10</option>
          <option>11</option>
          <option>12</option>
        </select>
        <label for="sel3">事件</label>
        <select class="form-control" id="eventType">
          <option>hide_transcript</option>
          <option>show_transcript</option>
          <option>load_video</option>
          <option>pause_video</option>
          <option>play_video</option>
          <option>seek_video</option>
          <option>speed_change_video</option>
          <option>stop_video</option>
        </select>
        <button type="button" class="btn btn-default" v-on:click="searchResult()">查詢</button>
      </div>
    </form>
  </div>
   <div id="loadVideo" class="draggable">
   <div class="draggable-header">折線圖</div>
      <div class="draggable-content">
        <div id="loadVideoChart" style="min-width: 600px; height: 400px; margin: 0 auto"></div>
      </div>
   </div>
   <div id="pieData" class="draggable">
   <div class="draggable-header">圓餅圖</div>
      <div class="draggable-content">
        <div id="pieChart" style="min-width: 500px; height: 400px; margin: 0 auto"></div>
      </div>
   </div>
   <div id="seekVideo" class="draggable">
   <div class="draggable-header">折線圖</div>
      <div class="draggable-content">
        <div id="seekVideoChart" style="min-width: 600px; height: 400px; margin: 0 auto"></div>
      </div>
   </div>
   <div id="monthChart" class="draggable">
   <div class="draggable-header">折線圖 - 月</div>
      <div class="draggable-content">
        <div id="logByMonthChart" style="min-width: 600px; height: 400px; margin: 0 auto"></div>
      </div>
   </div>
</div>
</div>
<script>
$(function() {
    $("#sortable").sortable();
    $("#sortable").disableSelection();
});

Vue.http.options.emulateJSON = true;
//$(function(){                   // Start when document ready
    var result = {};
    console.log("AAAAA");
    var sortable = new Vue({
        el: '#sortable',
        data: {
            result:{}
        },
        ready: function() {
            console.log("BBBB");
            this.getData();
            this.getSeekVideoData();
            this.getPieChart();
            this.getLogByMonth();
        },
        methods: {
            getData: function() {
                var vm = this;
                var data = {
                        eventType : $("#eventType").val(),
                        year :$("#year").val(),
                        month : $("#month").val(),
                        emulateJSON: true
                        //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                //console.log(vm.$http);
                vm.$http.post("home/searchType", data).then(function(response) {
                    console.log("response:", response.data);
                    result = response.data;
                    console.log("result:", result);
                    loadVideoChart(response.data, "loadVideoChart");
                    //vm.$set('result', response.data);
                });
            },
            getSeekVideoData: function() {
                var vm = this;
                var data = {
                        eventType : $("#eventType").val(),
                        year :$("#year").val(),
                        month : $("#month").val(),
                        emulateJSON: true
                        //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                //console.log(vm.$http);
                vm.$http.post("home/searchType", data).then(function(response) {
                    console.log("response seek_video:", response.data);
                    result = response.data;
                    console.log("result seek_video:", result + $("#year").val());
                    loadVideoChart(response.data, "seekVideoChart");
                    //vm.$set('result', response.data);
                });
            },
            getPieChart: function() {
                var vm = this;
                var data = {
                        year :$("#year").val(),
                        emulateJSON: true
                        //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                vm.$http.post("home/combinePie", data).then(function(response) {
                    console.log("response pie data:", response.data);
                    result = response.data;
                    console.log("result pie data:", result[0]);
                    pieChart(result, "pieChart");
                    //vm.$set('result', response.data);
                });
            },
            getLogByMonth: function() {
                var vm = this;
                var data = {
                        year : $("#year").val(),
                        month : $("#month").val(),
                        eventType : $("#eventType").val(),
                        emulateJSON: true
                        //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                //console.log(vm.$http);
                vm.$http.post("home/searchMonth", data).then(function(response) {
                    console.log("response getLogByMonth:", response.data);
                    result = response.data;
                    console.log("result getLogByMonth:", result);
                    loadVideoChart(result, "logByMonthChart");
                    //vm.$set('result', response.data);
                });
            },
            searchResult: function() {
                //alert('year:' + $("#year").val() + 'month:' + $("#month").val() + 'eventType:' + $("#eventType").val());
                this.getData();
                this.getSeekVideoData();
                this.getPieChart();
                this.getLogByMonth();
            }
        }
    });
    console.log("result outside:", result);
    //loadVideoChart(result);
//});
</script>
  </tiles:putAttribute>
</tiles:insertDefinition>

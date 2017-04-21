<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<tiles:insertDefinition name="classic">
<tiles:putAttribute name="title">EdXLog</tiles:putAttribute>
<tiles:putAttribute name="head">
<script src="${contextPath}/js/home/homeController.js"></script>
<style>
#edxsearch {
  list-style-type: none;
/*   margin-left: 2em; */
  padding: 0;
  width: 1100px;
}
</style>
</tiles:putAttribute>
<tiles:putAttribute name="content">
<div class="container">
<h1>EdXLog</h1>
<div id="edxsearch">
   <div class="well">
    <form class="form-inline">
      <div class="form-group">
        <label for="sel1">課程:</label>
        <select class="form-control" id="course" v-model="edxObj.course">
          <option value="">---請選擇---</option>
          <option v-for="course in resultCourse" v-bind:value="course.courseId">{{course.displayName}}</option>
        </select>
        <br>
        <label for="sel2">時間:</label>
          <div class="form-group">
              <div class='input-group date' id='datetimeStart'>
                  <input type='text' id='calenderStart' class="form-control" v-model="edxObj.calenderStart" style="width: 150px;"/>
                  <span class="input-group-addon">
                      <span class="glyphicon glyphicon-calendar"></span>
                  </span>
              </div>
              ~
              <div class='input-group date' id='datetimeEnd'>
                  <input type='text' id='calenderEnd' class="form-control" v-model="edxObj.calenderEnd" style="width: 150px;"/>
                  <span class="input-group-addon">
                      <span class="glyphicon glyphicon-calendar"></span>
                  </span>
              </div>
          </div>
        <br>
        <label for="sel3">排除角色:</label>
        <div class="checkbox" v-for="role in roleResult">
          <label>
            <input type="checkbox" id="{{role.id}}" value="{{role.name}}" v-model="edxObj.roles"> {{role.name}}
          </label>
        </div>
        <button type="button" class="btn btn-default" v-on:click="searchResult()">查詢</button>
      </div>
    </form>
  </div>
  <div v-if="isShow">
  <table class="table table-bordered">
    <thead>
      <tr class="info">
        <th>Id</th>
        <th>UserName</th>
        <th>EventType</th>
        <th>Event</th>
        <th>Time</th>
        <th>Context</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="edx in resultLog">
          <td>{{ edx.id }}</td>
          <td>{{ edx.userName }}</td>
          <td>{{ edx.eventType }}</td>
          <td>{{ edx.event }}</td>
          <td>{{ edx.dateTime }}</td>
          <td>{{ edx.context }}</td>
      </tr>
    </tbody>
  </table>
  </div>
</div>
</div>
<script>
$(function() {
    $('#datetimeStart').datetimepicker({
        locale: 'zh-tw',
        format: 'YYYY-MM-DD HH:mm'
    });
    $('#datetimeEnd').datetimepicker({
        locale: 'zh-tw',
        format: 'YYYY-MM-DD HH:mm'
    });
//     $("#datetimeStart").on("dp.change", function (e) {
//         $('#datetimeEnd').data("DateTimePicker").minDate(e.date);
//     });
//     $("#datetimeEnd").on("dp.change", function (e) {
//         $('#datetimeStart').data("DateTimePicker").maxDate(e.date);
//     });
});

Vue.http.options.emulateJSON = true;
    console.log("AAAAA");
    var roleCheckBox = [ {
        id : "instructor",
        name : "instructor"
    }, {
        id : "staff",
        name : "staff"
    }, {
        id : "beta_testers",
        name : "beta_testers"
    }, {
        id : "library_user",
        name : "library_user"
    } ];
    var sortable = new Vue({
        el : '#edxsearch',
        data : { //data主要是要給頁面所使用的
            resultLog : [],
            resultCourse : [],
            isShow : false,
            edxObj: {
                course: '',
                calenderStart: '',
                calenderEnd:'',
                roles: []
            },
            roleResult : roleCheckBox
        },
        ready: function() {
            console.log("BBBB");
            this.getCourse();
        },
        methods: {
            getCourse: function() {
                var vm = this;
                var data = {
                        emulateJSON: true
                        //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                //console.log(vm.$http);
                vm.$http.post("edxLog/listCourse", data).then(function(response) {
                    //console.log("response:", response.data);
                    //resultCourse = response.data;
                    vm.$set('resultCourse', response.data);
                });
            },
    //             getPieChart: function() {
    //                 var vm = this;
    //                 var data = {
    //                         year :$("#year").val(),
    //                         emulateJSON: true
    //                         //'Content-Type' : 'application/x-www-form-urlencoded'
    //                 };
    //                 vm.$http.post("home/combinePie", data).then(function(response) {
    //                     console.log("response pie data:", response.data);
    //                     result = response.data;
    //                     console.log("result pie data:", result[0]);
    //                     pieChart(result, "pieChart");
    //                     //vm.$set('result', response.data);
    //                 });
    //             },
    //             getLogByMonth: function() {
    //                 var vm = this;
    //                 var data = {
    //                         year : $("#year").val(),
    //                         month : $("#month").val(),
    //                         eventType : $("#eventType").val(),
    //                         emulateJSON: true
    //                         //'Content-Type' : 'application/x-www-form-urlencoded'
    //                 };
    //                 //console.log(vm.$http);
    //                 vm.$http.post("home/searchMonth", data).then(function(response) {
    //                     console.log("response getLogByMonth:", response.data);
    //                     result = response.data;
    //                     console.log("result getLogByMonth:", result);
    //                     loadVideoChart(result, "logByMonthChart");
    //                     //vm.$set('result', response.data);
    //                 });
    //             },
                searchResult: function() {
                    console.log("edxObj:", this.edxObj);
                    var roleStr = this.edxObj.roles.join(",")
                    console.log("roleStr:", roleStr);
                    var vm = this;
                    var data = {
                            courseId: this.edxObj.course,
                            calenderStart: this.edxObj.calenderStart,
                            calenderEnd: this.edxObj.calenderEnd,
                            roles: roleStr,
                            emulateJSON: true
                          //'Content-Type' : 'application/x-www-form-urlencoded'
                    };
                    console.log("data:", data);
                    vm.$http.post("edxLog/searchEdX", data).then(function(response) {
                        console.log("searchEdX:", response.data);
                        //resultCourse = response.data;
                        vm.$set('resultLog', response.data);
                        vm.$set('isShow', true);
                    });
                }
        }
    });
</script>
  </tiles:putAttribute>
</tiles:insertDefinition>
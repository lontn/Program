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
            <input type="checkbox" :id="role.id" :value="role.name" v-model="edxObj.roles"> {{role.name}}
          </label>
        </div>
        <button type="button" id="edxBtn" class="btn btn-default" v-on:click="searchResult()">查詢</button>
      </div>
    </form>
  </div>
  <div id="showTable" style="display: none">
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
    <tfoot>
      <tr>
          <td colspan="6">
              <nav aria-label="Page navigation" id="paginationBox">
                <ul class="pagination" id="pagination"></ul>
              </nav>
          </td>
      </tr>
    </tfoot>
  </table>
  </div>
  <div id="noDataTable" style="display: none">
 		查無此資料
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
});
var setPagination = function(totals, data, vm) {
    //用來解決分頁中沒有自動刷新的問題
    $('#pagination').remove();
    $('#paginationBox').append('<ul class="pagination" id="pagination"></ul>');
    //-------------------------
    $('#pagination').twbsPagination({
        totalPages: totals,
        visiblePages: 10,
        onPageClick: function (event, page) {
            console.log("page", page);
            data.pageIndex = page;
            vm.$http.post("edxLog/searchEdX", data).then(function(response) {
                console.log("searchEdX:", response.data);
                var edxLogList = response.data.edxLogList;
                //vm.$set('resultLog', edxLogList);
                vm.resultLog = edxLogList;
                $("#showTable" ).show();
            });
        }
    });
}
//$("#edxBtn").bind("click", setPagination(35, 1));
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
                calenderEnd: '',
                roles: []
            },
            roleResult : roleCheckBox
        },
        created: function() {
            console.log("BBBB");
            this.getCourse();
            console.log("CCC");
        },
        methods: {
            getCourse: function() {
                var vm = this;
                var data = {
                        emulateJSON: true
                        //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                //console.log(vm.$http);
                axios.post("edxLog/listCourse", data).then(function(response) {
//                     console.log("response:", response.data);
                    vm.resultCourse = response.data;
//                     vm.$set('resultCourse', response.data);
                }).catch(function (error) {
                    console.log(error);
                });
            },
            dateDefind: function() {
                $('#calenderStart').datetimepicker({
                    locale: 'zh-tw',
                    format: 'YYYY-MM-DD HH:mm'
                }).on("hide", function (e) {
                    var value = $("#calenderStart").val();
                    vm.edxObj.calenderStart = value;
                 });
                $('#calenderEnd').datetimepicker({
                    locale: 'zh-tw',
                    format: 'YYYY-MM-DD HH:mm'
                }).on("hide", function (e) {
                   var value = $("#calenderEnd").val();
                   vm.edxObj.calenderEnd = value;
                });
            },
            searchResult: function() {
                console.log("edxObj:", this.edxObj);
                console.log("calenderStart:", this.edxObj.calenderStart);
                var roleStr = this.edxObj.roles.join(",")
                console.log("roleStr:", roleStr);
                var vm = this;
                var data = {
                        courseId: this.edxObj.course,
                        calenderStart: this.edxObj.calenderStart,
                        calenderEnd: this.edxObj.calenderEnd,
                        roles: roleStr,
                        pageIndex: 0,
                        emulateJSON: true
                      //'Content-Type' : 'application/x-www-form-urlencoded'
                };
                console.log("data:", data);
                vm.$http.post("edxLog/searchEdX", data).then(function(response) {
                    console.log("searchEdX:", response.data);
                    var edxLogList = response.data.edxLogList;
                    if (edxLogList !=null) {
                        //vm.$set('resultLog', edxLogList);
                        vm.resultLog = edxLogList;
                        //vm.$set('isShow', true);
                        $("#showTable").show();
                        $("#noDataTable").hide();
                        var totalCount = response.data.totalCount;
                        var totalPage = Math.ceil(totalCount / 100);
                        setPagination(totalPage, data, vm);
                    } else {
                        $("#noDataTable").show();
                        $("#showTable").hide();
                    }
                }).catch(function (error) {
                    console.log("Err:", error);
                });
            }
        }
//         mounted: function() {
//             this.dateDefind();
//         }
    });
</script>
  </tiles:putAttribute>
</tiles:insertDefinition>
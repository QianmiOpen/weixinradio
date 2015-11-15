<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <title>千米网 - 历史事件</title>
  <link rel="shortcut icon" href="/img/favicon.ico">
  <link rel="stylesheet" href="/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="/css/carousel.css" >
  <script src="/js/jquery-1.11.3.min.js"></script>
  <script src="/js/jquery.twbsPagination.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li ><a href="/">首页</a></li>
        <li><a target="_blank" href="http://www.qianmi.com">千米网</a></li>
        <li><a target="_blank" href="http://ssm.qianmi.com">随手卖</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <c:if test="${user != null}">
          <li><a href="/${user == 'root' ? 'admin' : 'user'}">欢迎，${user}</a></li>
          <li><a href="/logout">退出</a></li>
        </c:if>
        <c:if test="${user == null}">
          <li><a href="/login">登录</a></li>
        </c:if>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <h2 class="logo"><img src="/img/logo.png" alt="">午夜Radio</h2>
      <ul class="nav nav-sidebar">
        <li class="nav-title">用户面板</li>
        <li><a href="/resource">资源管理</a></li>
        <li class="active"><a href="/event">历史事件</a></li>
        <li><a href="/user">个人设置</a></li>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <h1 class="page-header">历史事件</h1>


      <form class="form row" action="/event">
        <div class="col-md-4">
          <div class="col-md-9">
            <input type="text" name="contentKeyword" id="search" class="form-control" placeholder="搜索..." value="${contentKeyword}" autofocus="">
          </div>
          <div class="col-md-3">
            <input class="btn btn-info" type="submit" value="搜索" />
          </div>
        </div>
        <div class="col-md-8 text-right">共有 ${list.totalElements} 条记录</div>
      </form>

      <c:if test="${list.totalElements > 0}">
      <div class="table-responsive">
        <table class="table table-striped text-center">
          <thead>
          <tr>
            <th class="text-center" width="80">用户</th>
            <th class="text-left">事件内容</th>
            <th class="text-center" width="100">状态</th>
            <th class="text-center" width="140">发生时间</th>
            <th class="text-center" width="140">确认时间</th>
            <th class="text-center" width="100">操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${list.content}" var="event">
          <tr>
            <td>${event.workNo}</td>
            <td class="text-left">${event.content}</td>
            <td><c:if test="${event.status == 'INITIALIZATION'}"><font color="red">未确认</font></c:if><c:if test="${event.status == 'VERIFYER'}"><font color="green">已确认</font></c:if></td>
            <td><joda:format value="${event.occurredTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            <td><joda:format value="${event.confirmTime}"  pattern="yyyy-MM-dd HH:mm:ss" /></td>
            <td>
              <c:if test="${event.status == 'INITIALIZATION'}"><a href="">确认</a></c:if>
              <c:if test="${event.status == 'VERIFYER'}"><font color="gray">确认</font></c:if>
            </td>
          </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>

      <nav class="Tcenter">
        <ul id="pagination" class="pagination-sm"></ul>
      </nav>

        <script type="text/javascript">
          $(document).ready(function() {
            $('#pagination').twbsPagination({
              totalPages: ${list.totalPages},
              startPage: ${page + 1},
              visiblePages: 5,
              onPageClick: function (event, page) {
                location.href = '/event?page=' + page - 1;
              }
            });
          });
        </script>

      </c:if>

      <c:if test="${list.totalElements < 1 }">
      <div class="text-center">
        <dl class="sorry clearfix">
          <dt><img src="/img/sorry.png" /></dt>
          <dd>
            <h3>暂无数据！</h3>
            <%--<p>请点击右上角“新增”按钮添加规则。</p>--%>
          </dd>
        </dl>
      </div>
      </c:if>
    </div>
  </div>
</div>

</body>

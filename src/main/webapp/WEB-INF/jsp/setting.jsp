<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <title>千米网 - 个人设置</title>
  <link rel="shortcut icon" href="/img/favicon.ico">
  <link rel="stylesheet" href="/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="/css/carousel.css" >
  <script src="/js/jquery-1.11.3.min.js"></script>
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
        <li><a href="/event">历史事件</a></li>
        <li class="active"><a href="/user">个人设置</a></li>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <h1 class="page-header">个人设置</h1>

      <form class="form-horizontal" action="/user/${userSetting.workNo}" method="post">
        <div class="form-group">
          <label class="col-sm-2 control-label">最大资源数：</label>
          <div class="col-sm-10">
            <p class="form-control-static"><span>${userSetting.maxResource}</span>个</p>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">故障维护时间（默认）：</label>
          <div class="col-sm-10">
            <select class="form-control sm-input" name="maintainPeriodMinute">
              <option <c:if test="${userSetting.maintainPeriod/60 == 5}">selected</c:if>>5</option>
              <option <c:if test="${userSetting.maintainPeriod/60 == 10}">selected</c:if>>10</option>
              <option <c:if test="${userSetting.maintainPeriod/60 == 15}">selected</c:if>>15</option>
              <option <c:if test="${userSetting.maintainPeriod/60 == 30}">selected</c:if>>30</option>
              <option <c:if test="${userSetting.maintainPeriod/60 == 60}">selected</c:if>>60</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">微信绑定：</label>
          <div class="col-sm-10">
            <p class="form-control-static">
                <c:if test="${userSetting.wxOpenId != ''}">
                  <a href="/user/unbind">重新绑定？</a>
                </c:if>
            </p>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-info">保存</button>
            <button type="reset" class="btn btn-default">重置</button>
          </div>
        </div>
      </form>


    </div>
  </div>
</div>

</body>

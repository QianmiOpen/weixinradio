<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <title>千米网 - 管理台控制</title>
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
        <li class="nav-title">管理员面板</li>
        <li class="active"><a href="/admin">资源管理</a></li>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <h1 class="page-header">资源管理</h1>
      <form class="form row">
        <div class="col-md-4">
          <div class="col-md-9">
            <input type="text" id="search" class="form-control" placeholder="搜索..." required="" autofocus="">
          </div>
          <div class="col-md-3">
            <input class="btn btn-info" type="submit" value="搜索" />
          </div>
        </div>
        <div class="col-md-8 text-right">共有 3 条记录</div>
      </form>
      <div class="table-responsive">
        <table class="table table-striped text-center">
          <thead>
          <tr>
            <th class="text-center">编号</th>
            <th>URL</th>
            <th class="text-center">Method</th>
            <th class="text-center">用户</th>
            <th class="text-center">探测周期</th>
            <th class="text-center">最近一次探测时间</th>
            <th class="text-center">操作</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>1</td>
            <td class="text-left">http://www.baidu.com</td>
            <td>POST</td>
            <td>OF546</td>
            <td>5s</td>
            <td></td>
            <td><a href="">编辑</a><a href="">开启</a></td>
          </tr>
          <tr>
            <td>2</td>
            <td class="text-left">http://www.baidu.com</td>
            <td>GET</td>
            <td>OF546</td>
            <td>5s</td>
            <td></td>
            <td><a href="">编辑</a><a href="">开启</a></td>
          </tr>
          <tr>
            <td>3</td>
            <td class="text-left">http://www.baidu.com</td>
            <td>PUT</td>
            <td>OF546</td>
            <td>5s</td>
            <td></td>
            <td><a href="">编辑</a><a href="">开启</a></td>
          </tr>
          </tbody>
        </table>
      </div>
      <nav class="Tcenter">
        <ul class="pagination mePage">
          <li>
            <a href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li>
            <a href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
      <div class="text-center">
        <dl class="sorry clearfix">
          <dt><img src="/img/sorry.png" /></dt>
          <dd>
            <h3>暂无监控资源！</h3>
            <p>请点击右上角“新增”按钮添加规则。</p>
          </dd>
        </dl>
      </div>

      <!-- <div class="alert alert-warning Tcenter">
        <h1>暂无监控资源！</h1>
        <p>请点击右上角“新增”按钮添加规则。</p>
      </div> -->
    </div>
  </div>
</div>

</body>

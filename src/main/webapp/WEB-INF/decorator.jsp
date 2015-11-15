<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
      <meta charset="utf-8">
      <title>千米网 - <sitemesh:write property='title'/></title>
      <link rel="shortcut icon" href="/img/favicon.ico">
      <link rel="stylesheet" href="/css/bootstrap.min.css"/>
      <link rel="stylesheet" href="/css/carousel.css" >
      <script src="/js/jquery-1.11.3.min.js"></script>
      <sitemesh:write property='head'/>
    </head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
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

<sitemesh:write property='body'/>


</body>
</html>

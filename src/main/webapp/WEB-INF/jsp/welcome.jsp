<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>千米网 - 首页</title>
    <link rel="shortcut icon" href="/img/favicon.ico">
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/carousel.css" >
    <script src="/js/jquery-1.11.3.min.js"></script>
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

<div class="jumbotron">
    <div class="container">
        <h1><img src="/img/logo.png" alt="">午夜Radio</h1>
        <p>—更快更高效处理 IT 告警</p>
    </div>
</div>
<div class="container marketing">
    <div class="row featurette">
        <div class="col-md-7">
            <h2 class="featurette-heading">开箱即用</h2>
            <p class="lead">单机部署、弹性架构.</p>
        </div>
        <div class="col-md-5">
            <img class="featurette-image img-responsive center-block" src="/img/case.jpg" alt="">
        </div>
    </div>

    <hr class="featurette-divider">

    <div class="row featurette">
        <div class="col-md-7 col-md-push-5">
            <h2 class="featurette-heading">Oh yeah, it's that good. <span class="text-muted">See for yourself.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        </div>
        <div class="col-md-5 col-md-pull-7">
            <img class="featurette-image img-responsive center-block" src="/img/case.jpg" alt="">
        </div>
    </div>

    <hr class="featurette-divider">

    <div class="row featurette">
        <div class="col-md-7">
            <h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        </div>
        <div class="col-md-5">
            <img class="featurette-image img-responsive center-block" src="/img/case.jpg" alt="">
        </div>
    </div>
</div>

<div class="text-center attention">
    <ul class="list-inline clearfix">
        <li>关注我们</li>
        <li><img src="/img/anqlu.png" alt="" /></li>
        <li><img src="/img/mysoko.png" alt="" /></li>
    </ul>
</div>

<footer class="footer">
    <p class="text-center">&copy; 2015 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
</footer>

</body>

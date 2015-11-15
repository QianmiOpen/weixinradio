<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>千米网 - 微信绑定</title>
    <link rel="shortcut icon" href="/img/favicon.ico">
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/carousel.css" >
    <script src="/js/jquery-1.11.3.min.js"></script>
    <script src="/js/sockjs-0.3.4.js"></script>
    <script src="/js/stomp.js"></script>
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


<div class="container" style="margin-top: 100px;">
    <p>账号未绑定，请扫码。</p>
    <div id="msg" class="text-center">
        <h1>微信扫描绑定</h1>
        <p>
            <img src="${qrUrl}" alt="微信二维码" width="300" height="300"/>
        </p>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        var msg = $("#msg");
        // defined a connection to a new socket endpoint
        var socket = new SockJS('/stomp');
        var stompClient = Stomp.over(socket);

        stompClient.connect({ }, function(frame) {
            // subscribe to the /topic/message endpoint
            stompClient.subscribe("/topic/message", function(data) {
                var message = data.body;
                msg.html('<h1 style="color: #4cae4c">绑定成功，跳转中。。。</h1>');
                location.href = '/user';
            });
        });
    });
</script>

</body>
</html>

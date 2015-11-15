<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>千米网 - 事件</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/carousel.css"/>
</head>
<body class="confirm">
<div class="confirm-container">
    <ul class="list clearfix">
        <li class="list-title">发生时间</li>
        <li class="list-content"><joda:format value="${event.occurredTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
        <li class="list-title">用户编号</li>
        <li class="list-content">${event.workNo}</li>
        <li class="list-title">事件内容</li>
        <li class="list-content">${event.content}</li>
        <li class="list-title">事件状态</li>
        <li class="list-content">
            <c:if test="${event.status == 'INITIALIZATION'}"><font color="red">未确认</font></c:if>
            <c:if test="${event.status == 'VERIFYER'}"> <font color ="green">已确认</font></c:if>
        </li>
        <li class="list-title">确认时间</li>
        <li class="list-content"><joda:format value="${event.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
    </ul>
    <form class="form row" action="/event/${event.id}/confirm">
        <input type="submit" class="btn btn-lg btn-info btn-block" <c:if test="${event.status == 'VERIFYER'}"> disabled </c:if> value="确认"/>
    </form>
</div>
</body>
</html>

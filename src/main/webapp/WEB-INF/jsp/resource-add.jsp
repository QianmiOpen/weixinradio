<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <title>千米网 - 增加资源</title>
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
        <li class="active"><a href="/resource">资源管理</a></li>
        <li><a href="/event">历史事件</a></li>
        <li><a href="/user">个人设置</a></li>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <h1 class="page-header"><a href="/resource" class="pull-right btn btn-default">返回</a>资源增加</h1>

      <form action="/resource/add" method="post">
      <div class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2 control-label">探测周期：</label>
          <div class="col-sm-10">
            <label class="radio-inline">
              <input type="radio" name="detectPeriod" id="inlineRadio1" value="5" checked> 5s
            </label>
            <label class="radio-inline">
              <input type="radio" name="detectPeriod" id="inlineRadio2" value="10"> 10s
            </label>
            <label class="radio-inline">
              <input type="radio" name="detectPeriod" id="inlineRadio3" value="20"> 20s
            </label>
          </div>
        </div>
        <div class="form-group">
          <div class="col-lg-6 col-md-offset-1">
            <div class="input-group">
              <div class="input-group-btn">
                <button type="button" id="method_btn" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">GET <span class="caret"></span></button>
                <input type="hidden" name="method" id="method" value="get"/>
                <ul class="dropdown-menu" id="changeMethod">
                  <li><a href="#" data-method="get">GET</a></li>
                  <li><a href="#" data-method="post">POST</a></li>
                </ul>
              </div><!-- /btn-group -->
              <input name="url" id="url" type="text" class="form-control" aria-label="Text input with segmented button dropdown">
            </div><!-- /.input-group -->
          </div>
          <div class="col-lg-2">
            <button type="button" id="btn_send" class="btn btn-info">发送</button>
            <button type="submit" id="btn_save" class="btn btn-primary">保存</button>
          </div>
        </div>

      </div>

      </form>
      <%--<div class="form-inline hide" id="params">--%>
        <%--<div class="col-lg-6 col-md-offset-1">--%>
          <%--<p >--%>
            <%--<div class="form-group">--%>
              <%--<label class="sr-only" for="URL">URL Paramter Key</label>--%>
              <%--<input type="text" name="param" class="form-control param_name" placeholder="URL Paramter Key">--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
              <%--<label class="sr-only" >Value</label>--%>
              <%--<input type="text" name="param_value" class="form-control param_value" placeholder="Value">--%>
            <%--</div>--%>
            <%--<a class="delete" id="param_add" href="javascript:;">Add</a>--%>
          <%--</p>--%>

        <%--</div>--%>
      <%--</div>--%>

      <%--<div class="clearfix"></div>--%>
      <%--<div class="form-group">--%>
        <%--<div class="col-lg-6 col-md-offset-1">--%>
          <%--<ul class="nav nav-tabs">--%>
            <%--<li role="presentation" class="active"><a href="#">Headers (0)</a></li>--%>
            <%--<li role="presentation"><a href="#">body</a></li>--%>
          <%--</ul>--%>
        <%--</div>--%>
      <%--</div>--%>
      <%--<div class="form-inline ">--%>
        <%--<div class="col-lg-6 col-md-offset-1">--%>
          <%--<div>--%>
            <%--<div class="form-group">--%>
              <%--<label class="sr-only" for="URL">URL Paramter Key</label>--%>
              <%--<input type="email" class="form-control" placeholder="URL Paramter Key">--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
              <%--<label class="sr-only" >Value</label>--%>
              <%--<input type="password" class="form-control" placeholder="Value">--%>
            <%--</div>--%>
            <%--<a class="delete" href="javascript:;">Add</a>--%>
          <%--</div>--%>

        <%--</div>--%>
      <%--</div>--%>

      <div class="clearfix"></div>
      <hr class="featurette-divider minline">

      <div class="formarea">
        <div class="head col-md-offset-1">
          <p id="status" style="display: none"></p>
        </div>
        <div class="area" id="area" style="display: none"></div>
      </div>
</div>
</div>
</div>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript">

  $(document).ready(function() {
    $('#changeMethod > li > a').click(function(e) {
      var method = $(this).data("method");
      $('#method').val(method);
      $('#method_btn').html(method.toUpperCase() + ' <span class="caret"></span>');
    });

    $('#btn_send').click(function(e) {
      e.stopPropagation();
      var urlVal = $('#url').val();
      if ($.trim(urlVal).length < 1) {
        $('#url').focus();
        return false;
      }
      var urlMethod = $('#method').val();

      $('#status').hide();
      $("#area").hide();

      $.ajax({
        type: "POST",
        url: "/resource/send",
        data:  {url:urlVal, method:urlMethod},
        success: function(r){
          if (r.result == 'error') {
            $('#status').show().html('Status <span class="red">Error</span>');
            return;
          }

          $('#status').show().html('Status <span>OK</span>');
          $("#area").show().text(r.result);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          $('#status').show().html('Status <span class="red">Error</span>');
          $("#area").show().text(errorThrown);
        }
      });
//
//      $('#status').html('<img src="/img/loading.gif" alt="">');
//      $.post('/resource/send', {url:urlVal, method:urlMethod},function(r){
//        $('#status').html('Status <span>OK</span>');
//        $("area").html(r.result);
//      }, function(r) {
//        $('#status').html('Status <span class="red">Error</span>');
//        $("area").html(r);
//      });

//
//      $('#status').html('<img src="/img/loading.gif" alt="">');
//      $.ajax( {
//        url: urlVal,
//        type: urlMethod,
//        xhrFields: {
//          withCredentials: false
//        },
//        success:function(data) {
//          $('#status').html('Status <span>200</span><span>OK</span>');
//          $('#area').html(data);
//        },
//        error: function(data) {
//          $('#status').html('Status <span class="red">500</span><span class="red">Error</span>');
//          $('#area').html(data);
//        }
//      });

      //<img src="/img/loading.gif" alt="">Status <span>200</span><span>OK</span>  Time<span>41</span><span>ms</span></p>
      //<p>Status <span class="red">200</span><span>OK</span>  Time<span>41</span><span>ms</span>

    });

    $('a#link_params').click(function () {
      if ($('#params').hasClass('hide')) {
        $('#params').removeClass('hide');
        $(this).css("font-weight", "bold");
      } else {
        $(this).css("font-weight", "normal");
        $('#params').addClass('hide');
      }
    });

    $('#param_add').click(function() {
      $(this).parent().append('<div >' +
              '<div class="form-group">' +
              '<label class="sr-only" for="URL">URL Paramter Key</label>' +
              '<input type="text" name="param" class="form-control param_name" placeholder="URL Paramter Key">' +
              '</div>' +
                      '&nbsp;'+
              '<div class="form-group">' +
              '<label class="sr-only" >Value</label>' +
              ' <input type="text" name="param_value" class="form-control param_value" placeholder="Value">' +
              '</div>' +
              '<a class="delete param_delete" href="javascript:;">X</a>' +
              '</div>');

    });

    $(document).delegate('a.param_delete', 'click', function() {
      $(this).parent().remove();
    });

  });
</script>
</body>

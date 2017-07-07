<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh-CN">
  <head>
    <META NAME="BINDCARDSTATE" CONTENT="${bindState}"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>结果反馈</title>

    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/style/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
   <c:if test="${bindState eq 'bind_card_fail' }">
   <div class="media" style="width:360px; margin: 0 auto; margin-top: 60px; ">
      <div class="media-left">
          <img class="media-object" src="<%=request.getContextPath()%>/style/img/error_icon.png">
      </div>
      <div class="media-body  media-middle">
        <blockquote style=" border:none; padding-left: 10px; margin-bottom: 0px;">
          <p style="font-size: 26px; font-weight: bold;  color: #a94442">很抱歉，<span style="font-size:18px; ">${retmsg}！</span></p>
          <footer>${msg_detail}</footer>
        </blockquote>
      </div>
    </div>
    </c:if>
    <c:if test="${bindState eq 'bind_card_success' }">
    <div class="media" style="width:360px; margin: 0 auto; margin-top: 60px; ">
      <div class="media-left">
          <img class="media-object" src="<%=request.getContextPath()%>/style/img/success_icon.png">
      </div>
      <div class="media-body  media-middle">
        <blockquote style=" border:none; padding-left: 10px; margin-bottom: 0px;">
          <p style="font-size: 26px; font-weight: bold;  color: #3c763d">恭喜您，<span style="font-size:18px; ">${retmsg}！</span></p>
          <footer>
          ${msg_detail}
         </footer>
        </blockquote>
      </div>
    </div>
    </c:if>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%=request.getContextPath()%>/style/js/jquery-2.2.1.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=request.getContextPath()%>/style/js/bootstrap.min.js"></script>
  </body>
</html>
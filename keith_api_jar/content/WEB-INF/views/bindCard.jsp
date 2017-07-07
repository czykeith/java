<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>实名认证</title>

    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/style/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body style="background-color: #f2f2f2">
    <div class="container-fluid">
      <form style="margin-top: 20px;" name="form" action="<%=request.getContextPath()%>/oper/authentication_ump.asx" method="POST" id="postform">
        <input type="hidden" name="oper_id" value="${operId}" />
        <div class="form-group">
          <label for="exampleInputEmail1">姓名</label>
          <input type="text" class="form-control" name="oper_real_name" id="oper_real_name" placeholder="姓名">
        </div>
        <div class="form-group">
          <label for="exampleInputPassword1">身份证</label>
          <input type="text" class="form-control" name="oper_id_card" id="oper_id_card" placeholder="身份证">
        </div>
        <div class="form-group">
          <label for="exampleInputPassword1">银行名称</label>
          <select class="form-control" name="oper_bank_name">
            <c:forEach var="b" items="${bankList}">
            <c:if test="${b.bank_id !=30 }">
             <option value="${b.bank_id }">${b.bank_name }(单笔限额${b.single_money }元)</option>
            </c:if>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label for="exampleInputPassword1">银行卡号</label>
          <input type="tel" class="form-control" name="oper_bank_card" id="oper_bank_card" placeholder="银行卡号">
        </div>
        
        <div class="form-group">
          <label for="exampleInputPassword1">银行预留手机号</label>
          <input type="tel" class="form-control" name="oper_bank_mobile" id="oper_bank_mobile" placeholder="必须是银行预留手机号">
        </div>
        <!--  
         <div class="checkbox">
          <label>
            <input type="checkbox" id="tyxy" checked="checked" onclick="return false">同意<a  data-toggle="modal" data-target="#myModal">%%……%￥……协议</a>
          </label>
         </div>
       
         <div class="form-group">
          <label for="exampleInputPassword1">设置抢钱通平台交易密码</label>
          <input type="password" name="oper_pay_pwd" class="form-control" id="oper_pay_pwd" value="" placeholder="设置6位数字交易密码">
        </div>
          -->
        <a tabindex="0" id="postsubmit" onclick="checkDetail()" class="btn btn-lg btn-danger" role="button" data-toggle="popover" data-trigger="focus" title="错误提示！" data-content="">确定提交</a>
        <!-- <button type="button" class="btn btn-info btn-block" onclick="checkDetail()">确定</button> -->
      </form>
    </div>
    <!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" 
               aria-hidden="true">×
            </button>
            <h4 class="modal-title" id="myModalLabel">
               抢钱通资金托管协议
            </h4>
         </div>
         <div class="modal-body" style="height: 320px;overflow-y:auto;">
           
         </div>
         <div class="modal-footer">
           <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%=request.getContextPath()%>/style/js/jquery-2.2.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/style/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/style/js/md5.js"></script>
    <script type="text/javascript">
    function checkDetail(){
    	var rname=$("#oper_real_name").val();
    	var idcard=$("#oper_id_card").val();
    	var bcard=$("#oper_bank_card").val();
    	var bmobile=$("#oper_bank_mobile").val();
    	//var paypwd=$("#oper_pay_pwd").val();
    	//var bok=$("#tyxy").is(":checked");
    	if(rname==null||rname==""){
    		document.form.oper_real_name.focus(); 
    		alerterror("用户名不能为空");
    		return false;
    	}
		if(idcard.length!=15&&idcard.length!=18){
    		document.form.oper_id_card.focus(); 
    		alerterror("身份证号码有误");
    		return false;
    	}
    	if(bcard==null||bcard==""){
    		 document.form.oper_bank_card.focus(); 
    		 alerterror("银行卡信息有误");
    		return false;
    	}
    	var myreg = /^1[34578]\d{9}$/; 
        if(!myreg.test(bmobile)) 
        { 
            document.form.oper_bank_mobile.focus(); 
            alerterror("手机号码格式不正确");
            return false; 
        }
        // var payreg=/^\d{6}$/;
        //if(!payreg.test(paypwd)){
    		//	document.form.oper_pay_pwd.focus(); 
    		//		alerterror("交易密码必须为6位数字");
    		//	return false;
    		//}
    	//$("#oper_pay_pwd").val(hex_md5(paypwd));
    	$("#postform").submit();
    }
    function alerterror(emsg){
     $("#postsubmit").attr("data-content",emsg);
     $("#postsubmit").popover("show");
	}
    </script>
  </body>
</html>
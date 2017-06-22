<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>秒杀详情页</title>
<%@include file="common/header.jsp"%>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">
				<h1>${seckill.name }</h1>
			</div>
			<div class="panel-body">
				<h2 class="text-danger">
					<!-- 显示time图标 -->
					<span class="glyphicon glyphicon-time"></span>
					<span class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>
	</div>


	<div class="modal fade" id="killPhoneModal">
	
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>
					</h3>
				</div>
				
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhone"  placeholder="填写手机号^o^" class="form-control"/>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success"><span class="glyphicon glyphicon-phone"></span>submit</button>
				</div>
			</div>
		</div>
	</div>


</body>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
	<script type="text/javascript" src="/resources/script/seckill.js"></script>
	<script type="text/javascript">
		$(function(){
			
			var seckillId=${seckill.seckillId};
			var startTime=${seckill.startTime.time};
			var endTime=${seckill.endTime.time};
			//使用EL表达式传入参数
			seckill.detail.init({
				seckillId:seckillId,
				startTime:startTime,
				endTime:endTime
			});
		});
	</script>
	
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>Coupon Wallet</title>
		<%@include file="jqueryAllInclude.html" %>
		<%@include file="fbLoginAboveHeadJs.jsp" %>
		<%@include file="bootstrapHeadInclude.html" %>
		<script type="text/javascript">
			var it = it || {};
			$(function() {
				it.fb.setActionOnServerUp(function(data) {
					var resultData = $.parseJSON(data);
					var user = resultData.result;
					$('#userName').html('Welcome '+ user.name + '!');
				});
			});
		</script>
	</head>
	
	<body>
		<div class="it-nav navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<ul class="nav">
						<li class="active">
							<a href="#">Home</a>
						</li>
						<li><a href="#">About Us</a></li>
						<li><a href="#">Blog</a></li>
						<li><a href="#">Terms</a></li>
						<li><a href="#">Privacy</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="container" >
			<div class="row">
				<div class="span6" >
					Jump In to the Coupon Manager
				</div>
				<div class="span6" >
					<div >
						<input id="loginToFb" type="button" value="Login via Fb" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span6" >
					<span id="userName">
					</span>
				</div>
			</div>
		</div>
		
		
		
		<%@include file="fbInitAboveBodyEnd.jsp" %>
	</body>
</html>
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
					$('#goToWallet').submit();
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
				<div class="span9" >
					<div class="thumbnail">
						<img src="images/facebook.jpg" />
						<div class="caption" >
							<h3>
							Section Details:
							</h3>
							<p>
								Jump In to the Coupon Manager.
								This section will contain a video describing functionality.
							</p>
						</div>
					</div>
				</div>
				<div class="span3" >
					<div class="hero-unit" >
						<div class="row">
							<div class="well">
							This is another well container
							</div>
						</div>
						
						<div class="row">
							<form id="goToWallet" action="wallet.do">
								<input id="loginToFb" class="btn btn-large"  type="button" value="Login via Fb" />
								<input type="hidden" name="login" value="success" />
							</form>
						</div>
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
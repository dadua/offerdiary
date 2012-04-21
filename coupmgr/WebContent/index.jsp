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
		<%@include file="bootstrapHeadInclude.html" %>
		<%@include file="fbLoginAboveHeadJs.jsp" %>
		<script type="text/javascript">
			var it = it || {};
			$(function() {
					
				var onServerUpChange =function(data) {
					var resultData = $.parseJSON(data);
					var user = resultData.result;
					$('#userContainer').html('Welcome '+ user.name + '!').append(
							'<span><img src="http://graph.facebook.com/'+ user.userId +'/picture" /></span>'
							);
					$('#walletAction').show();
					$("#loginToFb").hide();
				};
		
				var onServerUpGotoWallet =function(data) {
					onServerUpChange(data);
					$('#goToWallet').submit();
				};
			 
				$('#loginToFb').click({onServerUp: onServerUpGotoWallet}, it.fb.checkAndLogin);
				it.fb.checkLoginStatusAndUpdateServer(function(){}, onServerUpChange);
			});
		</script>
		<style type="text/css">
			.mainHeading {
				color: #999;
				padding: 5px;
				text-align: center;
			}
			
			
			.footerLink{
				color: #234;
			}
		</style>
	</head>
	
	<body >
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container">
	        		<ul class="nav">
						<li class="active">
							<h1 class="mainHeading" >Coupon Wallet</h1>
						</li>
						<li>
						</li>
					</ul>
					<span class="pull-right mainHeading" id="userContainer" >
					</span>
        		</div>
      		</div>
 		</div>
		<div class="container" >
			<div class="row">
				<div class="span9" >
					<div class="thumbnail">
						<img src="images/CWallet.jpg"/>
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
					<div id="actionsContainer" class="hero-unit">
						<div class="row">
							<div class="well" id="walletAction" style="display: none;">
							<a class="btn btn-primary" href="wallet.do">Access my wallet</a>
							</div>
						</div>
						
						<div class="row">
							<form id="goToWallet" action="wallet.do" method="post">
								<input id="loginToFb" class="btn btn-large"  type="button" value="Login via Fb" />
								<input type="hidden" name="login" value="success" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
			
		<div class="row">
			<div class="container" >
				<ul class="nav nav-pills footerLink">
					<li class="active">
						<a href="#" class="footerLink"><i class="icon-home icon-white"></i>&nbsp;Home</a>
					</li>
					<li>
						<a href="#" class="footerLink"><i class="icon-volume-down icon-white"></i>&nbsp;About Us</a>
					</li>
					<li>
						<a href="#" class="footerLink"><i class="icon-pencil icon-white"></i>&nbsp; Blog</a>
					</li>
					<li>
						<a href="#" class="footerLink"><i class="icon-comment icon-white"></i>&nbsp;Terms</a>
					</li>
					<li>
						<a href="#" class="footerLink"><i class="icon-folder-close icon-white"></i>&nbsp;Privacy</a>
					</li>
				</ul>
			</div>
		</div>

		
		<%@include file="fbInitAboveBodyEnd.jsp" %>
	</body>
</html>
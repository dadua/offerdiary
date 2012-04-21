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
		<%@include file="commonHeader.jsp" %>
	</head>
	
	<body >
		<%@include file="navHeader.jsp" %>
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
		
		<%@include file="footer.jsp" %>

	</body>
</html>
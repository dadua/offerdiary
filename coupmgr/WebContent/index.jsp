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
				<div class="span7" >
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
				<div class="span5" >
					<div id="actionsContainer">
						<a class="btn btn-info btn-large" href="login.do">Login</a>
						<a class="btn btn-success btn-large" href="signup.do">Sign Up</a>
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>

	</body>
</html>
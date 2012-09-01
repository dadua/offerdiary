<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Sign Up to Offer Diary</title>
		
		<%@include file="common/header.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			
		</script>
		<style type="text/css">
			.fb-btn-color {
				background-color: #3B5998;
			}
			.inBetweenSmallText {
				color: #CCCCCC;
				font-size: 1.2em;
				margin-left: 40%;
			}
			
			.span2point5 {
				width: 212px;
			}
			
			.span-point-5 {
				width: 42px;
			}
			
			.input-center{
				padding-left:10%;
				padding-right:10%;
			}
			
		</style>
	</head>
	<body>
	
		<%@include file="common/navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<div class="span6 offset3">
					<div class="row">
						<div class="span4 offset4">
							<form id="goToWallet" action="wallet.do" method="post">
								<input id="loginToFb" class="btn btn-large btn-primary"  type="button" value="Sign up with Facebook" />
								<input type="hidden" name="login" value="success" />
							</form>
						</div>
					</div>
					<div class="row">
						<hr class="span3 offset2">
						<div class="span2">
							<span class="inBetweenSmallText">or</span>
						</div>
						<hr class="span3">
					</div>
					<div class="row">
						<div class="span6 offset3">
							<form action="emailSignup.do" method="post" class="" >
								<input type="text" class="input-xlarge input-center" name="email" placeholder="Email"></input>
								<%-- TODO: We might not require password field in signup page --%>
								<input type="password" class="input-xlarge input-center" name="password" placeholder="Password"></input>
								<div class="span6">
									<button type="submit" class="btn  btn-success ">
											Sign up
									</button>
								</div>
							</form>
						</div>
					</div>

				</div>
			</div>
			<%@include file="common/footer.jsp" %>
		</div>
	</body>
</html>
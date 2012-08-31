<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Sign Up to Offer Diary</title>
		
		<%@include file="commonHeader.jsp" %>
		
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
	
		<%@include file="navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<div class="span3" >
				&nbsp;
				</div>
				
				<div class="span6" >
				
					<div class="row">
						<div class="span2" >
						&nbsp;
						</div>
						
						<div class="span2">
							<form id="goToWallet" action="wallet.do" method="post">
								<input id="loginToFb" class="btn btn-large btn-primary"  type="button" value="Signup with Facebook" />
								<input type="hidden" name="login" value="success" />
							</form>
						</div>
						
						<div class="span2" >
						&nbsp;
						</div>
					</div>
					
					<hr class="span2" >
					<div class="span1" >
						<span class="inBetweenSmallText"> or </span>
					</div>
					<hr class="span2" >
					
					
					<div class="row">
						<div class="span1" >
						&nbsp;
						</div>	
						
						<div class="span4">
							<form action="emailSignup.do" method="post" class="" >
								<input type="text" class="input-xlarge input-center" name="email" placeholder="Email"></input>
								<%-- TODO: We might not require password field in signup page --%>
								<input type="password" class="input-xlarge input-center" name="password" placeholder="Password"></input>
								<div class="row">
									<div class="span2">
										<button type="submit" class="btn  btn-success ">Sign Up</button>	
									</div>
								</div>
							</form>
						</div>
					
						<div class="span1" >
						&nbsp;
						</div>	
					</div>
				</div>
				
				<div class="span3">
				&nbsp;
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
		
	</body>
</html>
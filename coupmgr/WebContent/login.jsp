<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login to Offer Diary</title>
		
		<%@include file="commonHeader.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			it.user = it.user || {};
			it.user.getCreds = function(){
				var email = $('#email').val(),
				password = $('#password').val();
				return {
					email: email,
					password: password
				};
			}
			
			it.user.login = function() {
				$.post('emailLogin.do', function(data) {
					var ret = $.parseJSON(data);
					if (ret.success === true) {
						$('#goToWallet').submit();
					} else {
						//Handle error case
					}
				});
			}
			
			$(function(){
				//$('#logIn').click(it.user.login);
			});
			
			
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
								<input id="loginToFb" class="btn btn-large btn-primary fb-btn-color"  type="button" value="Login with Facebook" />
								<input type="hidden" name="login" value="success" />
							</form>
						</div>
						
						<div class="span2" >
						&nbsp;
						</div>
					</div>
					
						
					<hr class="span2" >
					<div class="span1" >
						<span class="inBetweenSmallText">or</span>
					</div>
					<hr class="span2" >
					
					<div class="row">
						<div class="span1" >
						&nbsp;
						</div>	
						
						<div class="span3">
							<form action="emailLogin.do" method="post">
								<input id="email" type="text" class="input-xlarge" name="email" placeholder="Email"></input>
								<input id="password" type="password" class="input-xlarge" name="password" placeholder="Password"></input>
								<button id="logIn" type="submit" class="btn btn-info">Log in</button>
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
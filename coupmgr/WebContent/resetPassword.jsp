<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Get your password</title>
		
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
					<div class="row" style="margin-top:20%;">
						<div class="span1" >
						&nbsp;
						</div>	
						<div class="span4">
							<form action="emailLogin.do" method="post">
								<input id="email" type="text" class="input-xlarge input-center" name="email" placeholder="Email"></input>
								<div class="row">
									<div class="span2">
										<button id="getPassword" type="submit" class="btn btn-info">Get Password</button>
									</div>
									<div class="span2">
										<a href="login.do">
											Back to Login ?
										</a>
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
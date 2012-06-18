<%@page import="com.itech.web.LoginConstants"%>
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
				var email$ = $('#email'),
				email=email$.val(),
				password$ = $('#password'),
				password = password$.val(),
				returnNull = false;
				
				if (email=== '') {
					email$.siblings('.help-inline').show().hide('fade', 10000);
					email$.parent().addClass('error').removeClass('error', 10000);
					returnNull = true;
				}
				if (password === '') {
					password$.siblings('.help-inline').show().hide('fade', 10000);
					password$.parent().addClass('error').removeClass('error',10000);
					returnNull = true;
				}
				if (returnNull) {
					return null;
				}
				return {
					email: email,
					password: password
				};
			}
			
			it.user.login = function() {
				var creds = it.user.getCreds();
				if (creds === null) {
					return;
				}
				$.post('emailLogin.do',
						{'credVoKey': JSON.stringify(creds)},
						function(data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								$('#goToWallet').submit();
							} else if(ret.result === '<%=LoginConstants.INVALID_CREDS%>'){
								$('#invalidUserNamePassword').show().hide('fade', 10000).find('.close').click(function(){
									$(this).parent().hide();
								});
							} else {
								//Handle error case
							}
							
						});
			}
			
			$(function(){
				$('#logIn').click(it.user.login);
				var $invalidErrorMsg = $('#invalidUserNamePassword'),
				callLoginOnEnter = function(e) {
					$(this).siblings('.help-inline').hide().parent().removeClass('error');
					$invalidErrorMsg.hide();
					var code = e.keyCode ? e.keyCode : e.which;
					if(code.toString() == 13) {
						it.user.login();
					}
				}
				$('#email').keypress(callLoginOnEnter);
				$('#password').keypress(callLoginOnEnter);
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
						
						<div id="emailCredsForm" class="span3">
							<div id="invalidUserNamePassword" class="hide alert alert-error">
								<button class="close" type="button">×</button>
								Invalid email/password
							</div>
							
							<div class="form">
								<div class="control-group">
									<input id="email" type="text" class="input-xlarge input-center" name="email" placeholder="Email"></input>
									<span class="help-inline hide">*</span>
									
								</div>
								<div class="control-group">
									<input id="password" type="password" class="input-xlarge input-center" name="password" placeholder="Password"></input>
									<span class="help-inline hide">*</span>
								</div>
								<div class="row">
									<div class="span1">
										<button id="logIn" type="submit" class="btn btn-info">Log in</button>
									</div>
									<div class="span2">
										<a href="getPassword.do">
											forgot your password ?
										</a>
									</div>
								</div>
							</div>
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
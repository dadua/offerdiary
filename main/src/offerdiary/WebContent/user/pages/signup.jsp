<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Sign Up to Offer Diary</title>
		
		<%@include file="/common/pages/header.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			it.newUser = it.newUser || {};
			
			it.newUser.getCreds = function(){
				var name$ = $('#name');
				name =  name$.val();
				var email$ = $('#email');
				email=email$.val();
				var password$ = $('#password');
				password = password$.val();
				returnNull = false;
				if (name=== '') {
					name$.siblings('.help-inline').show().hide('fade', 10000);
					name$.parent().addClass('error').removeClass('error', 10000);
					returnNull = true;
				}
				if (email=== '' ) {
					email$.siblings('.help-inline').show().hide('fade', 10000);
					email$.parent().addClass('error').removeClass('error', 10000);
					returnNull = true;
				}
				if (!validateEmail(email)) {
					$('#invalidUserNamePassword').show().hide('fade', 10000).find('.close').click(function(){
						$(this).parent().hide();
					});
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
			
			it.newUser.signUp = function() {
				var creds = it.newUser.getCreds();
				if (creds === null) {
					return;
				}
				$('#toWallet').submit();
			}
			
			$(function(){
				$('#signup').click(it.newUser.signUp);
				var $invalidErrorMsg = $('#invalidUserNamePassword'),
				callLoginOnEnter = function(e) {
					$(this).siblings('.help-inline').hide().parent().removeClass('error');
					$invalidErrorMsg.hide();
					var code = e.keyCode ? e.keyCode : e.which;
					if(code.toString() == 13) {
						it.user.login();
					}
				}
				$('#name').keypress(callLoginOnEnter);
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
	
		<%@include file="/common/pages/navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<div class="span6 offset3">
					<div class="row">
						<div class="span4 offset4">
							<form id="goToWallet" action="wallet.do" method="post">
								<input id="loginToFb" class="btn btn-large btn-primary"  type="button" value="Connect with Facebook" />
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
							<div id="invalidUserNamePassword" class="hide alert alert-error">
								<button class="close" type="button">×</button>
								Invalid Email
							</div>
							<form id="toWallet" action="emailSignup.do" method="post" class="" >
								<div class="control-group">
									<input id="name" type="text"
										class="input-xlarge input-center" name="name"
										placeholder="Name" autocomplete="off"></input>
									<span class="help-inline hide">
										*
									</span>
								</div>
								<div class="control-group">
									<input id="email" type="text"
										class="input-xlarge input-center" name="email"
										placeholder="Email" autocomplete="off"></input>
									<span class="help-inline hide">
										*
									</span>
								</div>
								<%-- TODO: We might not require password field in signup page --%>
								<div class="control-group">
									<input id="password" type="password"
										class="input-xlarge input-center" name="password"
										placeholder="Password" autocomplete="off"></input>
									<span i class="help-inline hide">
										*
									</span>
								</div>
							</form>
							<div class="span6">
									<button type="submit" id="signup" class="btn  btn-success ">
											Sign up
									</button>
							</div>
						</div>
					</div>

				</div>
			</div>
			<%@include file="/common/pages/footer.jsp" %>
		</div>
	</body>
</html>
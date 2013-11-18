<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>
		<title>Login to Offer Diary</title>
		<script type="text/javascript">
			var it = it || {};
			it.user = it.user || {};
			it.user.getCreds = function(){
				var email$ = $('#email'),
				email=email$.val(),
				returnNull = false;
				if (email=== '') {
					email$.siblings('.help-inline').show().hide('fade', 10000);
					email$.parent().addClass('error').removeClass('error', 10000);
					returnNull = true;
				}
				if (returnNull) {
					return null;
				}
				return {
					email: email,
					password: ""
				};
			}
			
			it.user.getPassword = function() {
				var creds = it.user.getCreds();
				if (creds === null) {
					return;
				}
				$.post('verifyEmailForPassword.do',
						{'credVoKey': JSON.stringify(creds)},
						function(data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								$('#getPasswordRequest').submit();
							} else if(ret.result === '<%=LoginConstants.INVALID_CREDS%>'){
								$('#invalidEmail').show().hide('fade', 10000).find('.close').click(function(){
									$(this).parent().hide();
								});
							} else {
								//Handle error case
							}
							
						});
			}
			
			$(function(){
				$('#forgotPassword').click(it.user.getPassword);
				var $invalidErrorMsg = $('#invalidEmail'),
				callLoginOnEnter = function(e) {
					$(this).siblings('.help-inline').hide().parent().removeClass('error');
					$invalidErrorMsg.hide();
					var code = e.keyCode ? e.keyCode : e.which;
					if(code.toString() == 13) {
						it.user.login();
					}
				}
				$('#email').keypress(callLoginOnEnter);
			});
			
			
		</script>
		<style type="text/css">
			h1.forgot-password-heading{
				font-size: 28px;
				font-weight: bold;
				color: #0088CC;
			}
			h1.success-text{
				color:green;
			}
			span.email-send-message{
				font-family:  sans-serif;
				font-size: 16px;
				font-style: normal;
				font-weight: normal;
				text-transform: normal;
				line-height: 1.6em;
			}
			<%@include file="/common/css/layout.css" %>
			body {
				padding-top: 165px;
			}
			
			@media (max-width: 767px) {
			    .itemContainer {
			        margin-left: 20px;
			    }
			    body {
					padding-top: 0px;
				}
				
			}
			
		</style>
	</head>
	<body>
		<%@include file="/common/pages/navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<c:choose>
					<c:when test="${requestScope.forgotPasswordStatus=='false'}">
						<div class="span6 offset3"> 
							<div id="invalidEmail"
								class="hide alert alert-error">
								<button class="close" type="button">
									×
								</button>
								Invalid Email !
							</div>
							<h1 class="forgot-password-heading">
								Forgot your password?
							</h1>
							<form id="getPasswordRequest"
								action="gotPassword.do" method="post">
								<div class="span12">
									<div class="control-group">
										<input id="email" type="text"
											class="input-xlarge input-center" name="email"
											placeholder="Enter your email address"/>
										<span
											class="help-inline hide">
											*
										</span>
									</div>
								</div>
							</form>
							<div style="margin-left: 0%;"
								class="span6">
								<button id="forgotPassword"
									type="submit" class="btn  btn-success ">
									Submit
								</button>
							</div>
						</div>		
					</c:when>
					<c:otherwise>
						<div class="span6 offset3">
							<div class="row">
								<h1 class="forgot-password-heading success-text">
									Forgot your password ?
								</h1>
							</div>
							<div class="row">
								<span class="email-send-message">
									<strong>
										your password have been emailed to
										you. Please check your email
										account and follow the
										instructions.
									</strong>
								</span>
							</div>
							<div class="row">
								<div class="span12">
									<span class="email-send-message">
										Didn't receive the email?
									</span>
								</div>
							</div>
							<div class="row">
								<div class="span4">
									<a class="btn btn-primary"
										href="getPassword.do">
										Try again
									</a>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
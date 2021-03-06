<%@include file="/common/pages/headBegin.jsp" %>
		<title>Sign Up to VVS</title>
		
		<script type="text/javascript">
			var it = it || {};
			it.newUser = it.newUser || {};
			

			it.validateEmail = function (email) { 
			    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			    return re.test(email);
			}
			
			it.newUser.getCreds = function(){
				var name$ = $('#name'),
					name =  name$.val(),
					email$ = $('#email'),
					email=email$.val(),
					password$ = $('#password'),
					password = password$.val(),
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
				if (!it.validateEmail(email)) {
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
					callSignupOnEnter = function(e) {
						$(this).siblings('.help-inline').hide().parent().removeClass('error');
						$invalidErrorMsg.hide();
						var code = e.keyCode ? e.keyCode : e.which;
						if(code.toString() == 13) {
						    it.newUser.signUp();
						}
					};
				$('#name').keypress(callSignupOnEnter);
				$('#email').keypress(callSignupOnEnter);
				$('#password').keypress(callSignupOnEnter);
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
	
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<div class="row">
				<div class="span6 offset3">
					<img alt="VVans Sankalpa" src="images/logo_large.png" />
				</div>
			</div>
			<br />
			<br />
			<div class="row-fluid">
				<div class="span6 offset3">
					<div class="row">
						<div class="span6 offset3">
							<div id="invalidUserNamePassword" class="hide alert alert-error">
								<button class="close" type="button">�</button>
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
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
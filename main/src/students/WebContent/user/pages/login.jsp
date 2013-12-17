<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>

		<title>Login to VVS</title>
		<script type="text/javascript">
			var it = it || {};
			it.user = it.user || {};
			
			it.user.showLoginError = function () {
			    it.actionInfo.showErrorActionMsg('Username/Password incorrect');
			};
			
			it.user.getCreds = function(){
				var username$ = $('#username'),
				username=username$.val(),
				password$ = $('#password'),
				password = password$.val(),
				returnNull = false;
				
				if (username=== '') {
					username$.siblings('.help-inline').show().hide('fade', 10000);
					it.user.showLoginError ();
					returnNull = true;
				}
				if (password === '') {
					password$.siblings('.help-inline').show().hide('fade', 10000);
					it.user.showLoginError ();
					returnNull = true;
				}
				if (returnNull) {
					return null;
				}
				return {
					username: username,
					password: password
				};
			}
			
			it.user.login = function() {
				var creds = it.user.getCreds();
				if (creds === null) {
					return;
				}
				$.post('authenticate.do',
						creds,
						function(data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								$('<form action="home.do"></form>').appendTo('body').submit();
							} else if(ret.result === '<%=LoginConstants.INVALID_CREDS%>'){
							    it.user.showLoginError ();
							} else {
								//Handle error case
							}
							
						});
			}
			
			$(function(){
				$('#logIn').click(it.user.login);
				callLoginOnEnter = function(e) {
					$(this).siblings('.help-inline').hide().parent().removeClass('error');
					it.actionInfo.hideActionMsg();
					var code = e.keyCode ? e.keyCode : e.which;
					if(code.toString() == 13) {
						it.user.login();
					}
				}
				$('#username').keypress(callLoginOnEnter);
				$('#password').keypress(callLoginOnEnter);
			});
			
			
		</script>
		<style type="text/css">
			<%@include file="/common/css/layout.css" %>
			body {
				padding-top: 165px;
			}
		</style>

<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<br/>
			<br/>
			<br/>
			<div class="row">
				<img alt="VVans Sankalpa" src="images/logo_large.png" />
			</div>
			<div class="row">
				<div class="span6 offset3">
					<div class="container-fluid">
						<div class="row-fluid">
							<div id="userCredsForm" class="span6 offset3">
								<div class="form">
									<div class="control-group">
										<input id="username" type="text" class="input-xlarge input-center" name="username" placeholder="User Name"></input>
										<span class="help-inline hide">*</span>
										
									</div>
									<div class="control-group">
										<input id="password" type="password" class="input-xlarge input-center" name="password" placeholder="Password"></input>
										<span class="help-inline hide">*</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6 offset3">
								<button id="logIn" type="submit"
									class="btn btn-info">
									Log in
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br>
			<br>
			<br>
			<br>
			<br>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

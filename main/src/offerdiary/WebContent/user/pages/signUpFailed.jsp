<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>
		<title>Login to Offer Diary</title>
		
		<style type="text/css">
			h1.forgot-password-heading{
				font-size: 28px;
				font-weight: bold;
			}
			h1.failure-text{
				color:#b94a48;
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
		</style>
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<div class="row-fluid">
						<div class="span6 offset3">
							<div class="row">
								<h1 class="forgot-password-heading failure-text">
									Oops ! Sign Up Failed 
								</h1>
							</div>
							<div class="row">
								<span class="email-send-message">
									<strong>
										Looks like this email is already in use by some user.  
									</strong>
								</span>
							</div>
							<div class="row">
								<div class="span12">
									<span class="email-send-message">
										is that you ? please proceed to login  
									</span>
								</div>
							</div>
							<div class="row">
								<div class="span4">
									<a class="btn btn-primary"
										href="login.do">
										Login
									</a>
								</div>
							</div>
							<div class="row">
								<div class="span12">
									<span class="email-send-message">
										did you forget your password ?
									</span>
								</div>
							</div>
							<div class="row">
								<div class="span4">
									<a class="btn btn-primary"
										href="getPassword.do">
										Retrieve Password
									</a>
								</div>
							</div>
						</div>
			</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
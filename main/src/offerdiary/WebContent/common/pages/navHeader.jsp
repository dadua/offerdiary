

<%@page import="com.itech.user.model.LoginType"%>
<%@page import="com.itech.user.model.User"%>
<style>
div.navbar-inner-header{
				background-color: #A4A2A2;
				background-image: -webkit-linear-gradient(right, #333, #FFFFFF);
				background-image: -moz-linear-gradient(right , #333, #FFFFFF);
			}
a.logo-background{
	background-image:url(images/logo_tag.png);
	background-size: 100% 100%;
	background-repeat: no-repeat;
	padding : 10px;
	width: 80%;
	height: 50%;
	display:block;
	overflow: visible;
}
.height-100{
	height: 100%;
}
div.tile-div{
	height: 100px;
	padding: 10px;
	text-align:center;
}
</style>
		<div class="navbar navbar-fixed-top" style="height: 12%;" >
      		<div class="navbar-inner navbar-inner-header height-100">
        		<div class="container height-100">
	        		<ul class="nav height-100" style="width: 60%">
						<li style="width: 40%; height: 100%; margin-left: 10%;">
							<h1 class="mainHeading" style="width: 100%; height: 100%;"><a class="logo-background"  href="home.do" ></a></h1>
						</li>
						<li>
						</li>
					</ul>
					<%
						Object userSessionObj = request.getSession().getAttribute("user_session_key");
								if (userSessionObj!= null) {
					%>
						<div class="pull-right btn-group" id="userContainer" >
						<%
							User user = (User) userSessionObj;
						%>
						<a class="btn btn-info" href="wallet.do">
						<%
						if(user.getName()!=null) {
						%>
							<%=user.getName()%> 
						<%
						} else if (user.getEmailId()!=null) {
						%>
							<%=user.getEmailId() %>
						<%
						} else {
						%>
							<%=user.getUserId()%>
						<%
						}
						%>
						
						<%
						if (user.getLoginType() == LoginType.FACEBOOK) {
						 %>
						 <img class="tinyPic" width="20px"  height="20px" src="https://graph.facebook.com/<%=user.getUserId()%>/picture?type=square" />
						 <%
						} 
						%>
						
						</a>
						<%
						if (user.getLoginType() == LoginType.FACEBOOK) {
						 %>
						<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
						<%
						} else {
						%>
						<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
						<%
						} 
						%>
						
						<span class="caret"></span>
						</a>
						
						<ul class="dropdown-menu">

							<li>
								<a href="wallet.do">
								My Account
								</a>
							</li>
							<li>
								<a href="logout.do">
								Logout
								</a>
							</li>
						</ul>
					</div>
					<%
					 } else {
					 %>
					 	<div id="actionsContainer" class="pull-right ">
					 		<a class="btn btn-info btn-large" href="login.do">Login</a>
							<a class="btn btn-success btn-large" href="signup.do">Sign Up</a>
						</div>
					 <%
					 }
					 %>
        		</div>
      		</div>
 		</div>
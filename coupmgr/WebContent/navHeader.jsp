
<%@page import="com.itech.coupon.model.LoginType"%>
<%@page import="com.itech.coupon.model.User"%>
		
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container">
	        		<ul class="nav">
						<li>
							<h1 class="mainHeading" ><a class="btn btn-inverse btn-large" href="home.do" >Coupon Wallet</a></h1>
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
						<a class="btn btn-inverse">
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
						 <img class="tinyPic" width="23px"  height="23px" src="https://graph.facebook.com/<%=user.getUserId()%>/picture?type=square" />
						 <%
						} 
						%>
						</a>
						<a class="btn btn-large btn-inverse dropdown-toggle" data-toggle="dropdown" href="#">
						<span class="caret"></span>
						</a>
						
						<ul class="dropdown-menu">
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
					 	<div id="actionsContainer" class="pull-right">
					 		<a class="btn btn-info btn-large" href="login.do">Login</a>
							<a class="btn btn-success btn-large" href="signup.do">Sign Up</a>
						</div>
					 <%
					 }
					 %>
        		</div>
      		</div>
 		</div>
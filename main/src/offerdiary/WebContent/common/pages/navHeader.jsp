<%@page import="org.apache.jasper.tagplugins.jstl.core.Choose"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@page import="com.itech.user.model.LoginType"%>
<%@page import="com.itech.user.model.User"%>
<style>
div.navbar-inner-header{
				background-color: #A4A2A2;
			}


.topAlign {
	margin-top: 3%;
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
					
					<c:choose>
						<c:when test="${userSession !=null}">
						<div class="pull-right btn-group topAlign" id="userContainer" >
						<a class="btn btn-info" href="wallet.do">
							<c:choose>
								<c:when test="${userSession.name != null}">
									${userSession.name}
								</c:when>
								<c:otherwise>
									${userSession.emailId}
									
								</c:otherwise>
							</c:choose>
							
							<c:if test="${userSession.loginType.name == 'FACEBOOK' }" >
								 <img class="tinyPic" width="20px"  height="20px" src="https://graph.facebook.com/${userSession.userId}/picture?type=square" />
							</c:if>
						</a>
						<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
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
				</c:when>
					<c:otherwise>
					 	<div id="actionsContainer" class="pull-right topAlign">
					 		<a class="btn btn-info btn-large" href="login.do">Login</a>
							<a class="btn btn-success btn-large" href="signup.do">Sign Up</a>
						</div>
						</c:otherwise>
					</c:choose>
        		</div>
      		</div>
 		</div>
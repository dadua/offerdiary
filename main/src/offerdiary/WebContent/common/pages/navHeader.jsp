<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@page import="com.itech.user.model.LoginType"%>
<%@page import="com.itech.user.model.User"%>
	<style>
		.topAlign {
			margin-top: 10px;
		}
		div.tile-div{
			height: 100px;
			padding: 10px;
			text-align:center;
		}
		.brandImage {
			height: 50px;
			width: 160px;
		}
		
		.featureTabs {
			margin-left: 30px;
			margin-top: 30px;
		}
		</style>
		
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container navBarItems">
        			<a class="brand" href="home.do">
        				<%--
        				Offer Diary
        				 --%>
        				 
        				<img src="images/logo_tag.png" class="brandImage" alt="Offer Diary" ></img>
        			</a>
        			<c:if test="${userSession!=null}">
	     				<ul class="featureTabs nav nav-tabs " style="margin-left: 25px; margin-top:30px;">
							<li id="offerTab" >
								 <a href="offers.do" >Offers</a>
							</li>
							<li id="alertTab" >
								<a href="alerts.do" >Notifications</a>
							</li>
							<li id="cardTab" >
								<a href="cards.do" >Cards</a>
							</li>
							<li id="profileTab" >
								<a href="profile.do" >Profile</a>
							</li>
						</ul>
					</c:if>
					<c:choose>
						<c:when test="${userSession !=null}">
							<div class="topAlign pull-right btn-group" id="userContainer" style="margin-top: 2%;">
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
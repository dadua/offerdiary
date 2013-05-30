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
		}
		
		.featureTabs {
			margin-left: 30px;
			margin-top: 30px;
		}
		.tinyPic {
			width: 19px;
			height: 19px;
		}
		
		.fbLikeContainer {
		    margin-top: 2.4%;
		    margin-right: 20px;
		}

		</style>
		
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container navBarItems">
        			<a class="brand" href="home.do">
        				<%--
        				Offer Diary
        				 --%>
        				 
        				<img src="images/logo_tag.png" class="brandImage" alt="Offer Diary" />
        			</a>
        			
        			
        			<div class="btn btn-navbar topAlign pull-right" data-toggle="collapse" data-target=".nav-collapse">
	        			<span class="icon-bar"></span>
	        			<span class="icon-bar"></span>
	        			<span class="icon-bar"></span>
        			</div>
        			
	
        			<div class="nav-collapse collapse">
	        			<c:if test="${userSession!=null}">
		     				<ul class="featureTabs nav nav-tabs" style="margin-left: 25px; margin-top:30px;color:#0088CC;">
		     					<li class="divider-vertical"></li>
								<li class="featureTab" id="publicOfferTab" >
									 <a href="offers.do" style="color:#0088CC;">Find Offers</a>
								</li>
		     					<li class="divider-vertical"></li>
								<li class="featureTab" id="offerTab" >
									 <a href="myoffers.do" style="color:#0088CC;">My Offers</a>
								</li>
								<li class="divider-vertical"></li>
								<%--
								<li id="alertTab" >
									<a href="alerts.do" style="color:#0088CC;" >Notifications</a>
								</li>
								<li class="divider-vertical"></li>
								 --%>
								<li class="featureTab" id="cardTab" >
									<a href="cards.do" style="color:#0088CC;">Cards</a>
								</li>
								<li class="divider-vertical"></li>
								<li class="featureTab" id="profileTab" >
									<a href="profile.do" style="color:#0088CC;">Profile</a>
								</li>
								<li class="divider-vertical"></li>
								<li class="visible-phone">
									<a href="logout.do">
									Logout
									</a>
								</li>
							</ul>
						</c:if>
						
						<c:choose>
							<c:when test="${userSession !=null}">
								<div class="topAlign pull-right btn-group hidden-phone" id="userContainer" style="margin-top: 2%;">
									<a class="btn btn-info" href="profile.do">
										<c:choose>
											<c:when test="${userSession.name != null}">
												${userSession.name}
											</c:when>
											<c:otherwise>
												${userSession.emailId}
												
											</c:otherwise>
										</c:choose>
										<c:if test="${userSession.loginType.name == 'FACEBOOK' }" >
											 <img class="tinyPic" src="https://graph.facebook.com/${userSession.userId}/picture?type=square" />
										</c:if>
									</a>
									<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
										<span class="caret"></span>
									</a>
									
									<ul class="dropdown-menu">
			
										<li>
											<a href="diary.do">
											OfferDiary
											</a>
										</li>
										<li>
											<a href="cards.do">
											Cards
											</a>
										</li>
										<li>
											<a href="profile.do">
											Profile
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
							 		<c:if test="${pageAttrKey!='login'}">
								 		<a class="btn btn-info btn-large" href="login.do">Login</a>
							 		</c:if>
							 		
							 		<c:if test="${pageAttrKey!='signup'}">
										<a class="btn btn-success btn-large" href="signup.do">Sign Up</a>
									</c:if>
								</div>
							</c:otherwise>
						</c:choose>
						
					</div>
					
					<div class="fbLikeContainer hidden-phone topAlign pull-right">
						<div class="fb-like" data-href="https://www.facebook.com/offerdiary" data-send="false" data-layout="button_count" data-width="140" data-show-faces="false"></div>
					</div>
        		</div>
      		</div>
      		<div class="container">
	       		<%@include file="/common/pages/actionInfoRow.jsp" %>
       		</div>
 		</div>
 		<script type="text/javascript" >
 			$(function(){
	 			$('.featureTabs > .active + .divider-vertical').hide().prev().prev().hide()
 			});
 		
 		</script>
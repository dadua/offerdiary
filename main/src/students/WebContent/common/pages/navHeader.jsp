<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

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
		}
		
		.featureTabs {
			margin-left: 30px;
			margin-top: 30px;
		}
		.tinyPic {
			width: 19px;
			height: 19px;
		}
		
		</style>
		
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container navBarItems">
        			<a class="brand" href="home.do">
        				<img src="images/logo_tag.png" class="brandImage" alt="VVans Sankalpa" />
        			</a>
        			<c:choose>
	        			<c:when  test="${userSession!=null}">
		        			<div class="pull-right">
			        			<a href="logout.do" class="btn">Logout</a>
		        			</div>
	        			</c:when>
	        			<c:otherwise>
		        			<div id="actionsContainer" class="pull-right">
		        				<c:if test="${pageAttrKey!='login'}">
				        			<a class="btn btn-info " href="login.do">Login</a>
			        			</c:if>
		        				<c:if test="${pageAttrKey!='signup'}">
									<a class="btn btn-success " href="signup.do">Sign Up</a>
								</c:if>
							</div>
	        			</c:otherwise>
        			</c:choose>
	        		<c:if test="${userSession!=null}">
	       				<ul class="featureTabs nav nav-tabs" style="margin-left: 25px; margin-top:5px;color:#0088CC;">
		       				<li class="divider-vertical"></li>
		       				<li class="featureTab" id="profileTab" >
			       				<a href="profile.do" style="color:#0088CC;">My Profile</a>
		       				</li>
		       				<li class="divider-vertical"></li>
		       				<li class="featureTab" id="alumniTab" >
			       				<a href="alumni.do" style="color:#0088CC;">Alumni</a>
			       			</li>
			       			<li class="divider-vertical"></li>
			       			<li class="featureTab" id="blogTab" >
				       			<a href="http://www.vvans-sankalpa.org/" style="color:#0088CC;">Blog</a>
			       			</li>
			       			<li class="divider-vertical"></li>
						</ul>
					</c:if>
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
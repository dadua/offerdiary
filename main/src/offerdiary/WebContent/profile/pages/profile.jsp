<%@page import="com.itech.alert.model.Alert" %>
<%@page import="java.util.List" %>
<%@include file="/common/pages/headBegin.jsp" %>
	<title>Profile</title>
	
	
	<script src="profile/js/profile.js"></script>
	<script src="profile/js/userinfo.js"></script>
	<script src="profile/js/notificationconfig.js"></script>
	<script src="profile/js/passwordchange.js"></script>
	<script src="common/libs/history.js/scripts/bundled/html4+html5/jquery.history.js"></script>

	<script type="text/javascript">

		$(function(){
			$('#profileTab').addClass('active');
			it.profile.init('${option}');
		});
		
		
	</script>
	<style type="text/css">
		<%@include file="/common/css/layout.css" %>
		<%@include file="/profile/css/profile.css" %>
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<%@include file="/common/pages/featureTabsRow.jsp" %>
			<div class="row">
				<div class="span2" >
                	<div class="span2 container-fluid options-left-container">
                		<div class="img-polaroid">
                		<%--
                			<img src="https://graph.facebook.com/alok.id/picture?type=large" />
                			<img id="profilePic" src="images/profile/male.jpg" width="200" height="200" />
                			<img id="profilePic" src="images/profile/female.jpg" width="200" height="200" />
                			 --%>
                			<img id="profilePic" src="${userInfo.profileImgUrl}" width="200" height="200" />
                		</div>
                		
	                	<ul class="nav nav-list">
							<li class="" ><a id="userDetails" href="?o=details">Details</a></li>
							<li><a href="?o=notificationSettings" id="notificationSettings">Notification Settings</a></li>
							<c:if test="${!isLinkedUser}">
								<li><a href="?o=changePassword" id="changePasswordOption">Change Password</a></li>
							</c:if>
							<%--
							<li><a href="#">Points</a></li>
							<li><a href="#">Linked Accounts</a></li>
							<li class="divider"></li>
							<li class="nav-header">Friends:</li>
							<li><a href="#">Shared</a></li>
							<li><a href="#">Blocked</a></li>
							 --%>
						</ul>
					</div>
                </div>
                
				<div class="span10 content-container"  >
					<div class="container-fluid" id="profileContainer" >
					</div>
				</div>
			</div>
		</div>
		<div class="hide" style="display:none">
			<%@include file="/profile/templates/userDetails.html" %>
			<%@include file="/profile/templates/notificationConfig.html" %>
			<%@include file="/profile/templates/passwordChange.html" %>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

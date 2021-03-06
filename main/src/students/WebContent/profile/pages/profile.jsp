<%@page import="java.util.List" %>
<%@include file="/common/pages/headBegin.jsp" %>
	<title>Profile</title>
	
	
	<script src="profile/js/profile.js"></script>
	<script src="profile/js/userinfo.js"></script>
	<script src="profile/js/passwordchange.js"></script>
	<script src="common/libs/history.js/scripts/bundled/html4+html5/jquery.history.js"></script>
	<script src="common/libs/jQuery-File-Upload-9.5.0/js/jquery.iframe-transport.js"></script>
	<script src="common/libs/jQuery-File-Upload-9.5.0/js/jquery.fileupload.js"></script>

	<script type="text/javascript">

		$(function(){
			$('#profileTab').addClass('active');
			it.profile.init('${option}');
			
			/*
			$('#uploadProfilePic').fileupload({
			    dataType: 'json',
			    done: function(e, data) {
					$.noop;
			    },
			    url: 'uploadProfilePic.do'
			});
			
			$('#initUploadBtn').click(function(e) {
			    $('#uploadProfilePic').fileupload({
					'add'
			    });
			});
			*/
		});
		
		
	</script>
	<style type="text/css">
		<%@include file="/common/css/layout.css" %>
		<%@include file="/profile/css/profile.css" %>
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
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
							<c:if test="${!isLinkedUser}">
								<li><a href="?o=changePassword" id="changePasswordOption">Change Password</a></li>
							</c:if>
							<li><a href="?o=changeProfilePic" id="changeProfilePicOption">Change Profile Pic</a></li>
							
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
                
				<div class="span10 "  >
					<div class="container-fluid content-container" id="profileContainer" >
					</div>
					
					
				</div>
			</div>
		</div>
		<div class="hide" style="display:none">
			<%@include file="/profile/templates/userDetails.html" %>
			<%@include file="/profile/templates/passwordChange.html" %>
			<div class="update_profile_pic_template">
				<div class="container-fluid">
					<div class="row-fluid">
		                <span class="bluishText largeTitleFontSize">
		                    <strong>
		                    Upload your Profile Pic 
		                    </strong>
		                </span>
	                </div>
	                <br />
	                <br />
 
	 				<div class="row-fluid">
						<form method="POST" enctype="multipart/form-data" action="updateProfilePic.do">
							File to upload: <input id="uploadProfilePic" type="file" name="upfile" > </input>
							<input type="submit" id="submitProfilePic" class="disabled" value="Press" class="btn btn-info" > to upload the file!</input>
						</form>
					</div>
                </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

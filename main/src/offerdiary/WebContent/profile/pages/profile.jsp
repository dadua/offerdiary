<%@page import="com.itech.alert.model.Alert" %>
<%@page import="java.util.List" %>
<%@include file="/common/pages/headBegin.jsp" %>
	<title>Profile</title>
	
	
	<script type="text/javascript">
		var it = it || {};

		$(function(){
			$('div.tabbable ul.nav li').removeClass('active');
			$('#profileTab').addClass('active');
		});
		
		
	</script>
	<style type="text/css">
		<%@include file="/common/css/layout.css" %>
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
	    	<%@include file="/common/pages/actionInfoRow.jsp" %>
			<%@include file="/common/pages/featureTabsRow.jsp" %>
			<div class="row">
				<div class="span2" >
                	<div class="container-fluid options-left-container">
	                	<ul class="nav nav-list">
			                <li class="nav-header">User:</li>
							<li class="active"><a href="#">Details</a></li>
							<li><a href="#">Notification Settings</a></li>
							<li><a href="#">Points</a></li>
							<li><a href="#">Linked Accounts</a></li>
							<li class="divider"></li>
							<li class="nav-header">Friends:</li>
							<li><a href="#">Shared</a></li>
							<li><a href="#">Blocked</a></li>
						</ul>
					</div>
                </div>
                
				<div class="span10 content-container" id="profileContainer" >
					<div class="container-fluid">
						<div class="row-fluid">
						</div>
					</div>
				</div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

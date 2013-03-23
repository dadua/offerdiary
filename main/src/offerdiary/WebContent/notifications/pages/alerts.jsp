<%@page import="com.itech.alert.model.Alert" %>
<%@page import="java.util.List" %>
<%@include file="/common/pages/headBegin.jsp" %>
		<title>Notifications</title>
		
		
		<script type="text/javascript">
			var it = it || {};
			it.wallet = it.wallet || {};
						
			it.wallet.dismissAlert = function(e) {
				var target = e.target;
				var targetId = target.id;
				var alertIdExtractRegex = /^alertDismiss_(.*)/;
				var alertId = alertIdExtractRegex.exec(targetId)[1];
				var alertIds = [];
				alertIds.push(alertId);
				$.post('deleteAlerts.do', {'alertIds': JSON.stringify(alertIds)}, function(data) {
					var ret = $.parseJSON(data);
					if (ret.success === true) {
						$('#alert_'+alertId).remove();
					} else {
						//Handle error case
					}
				});
			};
			
			it.wallet.addHandlers = function () {
				$('.icon-trash').hover(function(e) {
					$(this).removeClass('icon-white');
				}, function(e){
					$(this).addClass('icon-white');
				});
				$('.alert-dismiss').click(it.wallet.dismissAlert);
			};

			$(function(){
				$('#alertTab').addClass('active');
				it.wallet.addHandlers();
			});
			
			
		</script>
		<style type="text/css">
			<%@include file="/common/css/layout.css" %>
			.alert {
				background: none repeat scroll 0 0 #CCCCFF;
			};
			
		</style>
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<%@include file="/common/pages/featureTabsRow.jsp" %>
			<div class="row">
				<div class="span2" >
                	<div class="container-fluid options-left-container">
	                	<ul class="nav nav-list">
			                <li class="nav-header">Notifications:</li>
							<li class="active"><a href="#">All</a></li>
							<li><a href="#">On my offers</a></li>
							<li><a href="#">On Offer on my cards</a></li>
							<li><a href="#">By my Friends</a></li>
							<li class="divider"></li>
							<li class="nav-header">Other filters:</li>
							<li><a href="#">Snoozed</a></li>
							<li><a href="#">Actionable</a></li>
							<li><a href="#">Dismissed</a></li>
						</ul>
					</div>
                </div>
                
				<div class="span10 content-container" id="alertContainer" >
					<div class="container-fluid">
						<div class="row-fluid">
							 <% 
							 	List<Alert> myAlerts = (List<Alert>) request.getAttribute("myAlerts");
							 %>	
							 
							 <ul class="thumbnails">
							 <% 
							    for (Alert alert : myAlerts) {
							    	%>
							    		<li class="span3" id="alert_<%=alert.getId()%>">
											<div class="thumbnail alert"  >
											<span class="icon-trash icon-white pull-right alert-dismiss" id="alertDismiss_<%=alert.getId()%>"></span>
											<h3> <%=alert.getMessage()%> </h3>
											</div>
										</li>
							    	<%
								}
							%>
							</ul>
						</div>
					</div>
					
				</div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
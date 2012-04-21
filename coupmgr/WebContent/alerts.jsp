<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@page import="java.util.List"%>
<%@page import="com.itech.coupon.model.Coupon"%>
<%@page import="com.itech.alert.model.Alert"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>My Alerts</title>
		
		<%@include file="commonHeader.jsp" %>
		
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
				it.wallet.addHandlers();
				$('div.tabbable ul.nav li').removeClass('active');
				$('#alertTab').addClass('active');
			});
			
			
		</script>
		<style type="text/css">
			.alert {
				background: none repeat scroll 0 0 #CCCCFF;
			};
			
		</style>
	</head>
	<body>
	
		<%@include file="navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<div class="span2" >
					<%@include file="walletTabs.jsp" %>
				</div>
				<div class="span10" id="alertContainer" >
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
				&nbsp;
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
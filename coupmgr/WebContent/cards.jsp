<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@page import="java.util.List"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>My Cards</title>
		
		<%@include file="commonHeader.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			it.wallet = it.wallet || {};
						
			it.wallet.dismissCard = function(e) {
				var target = e.target;
				var targetId = target.id;
				var cardIdExtractRegex = /^cardDismiss_(.*)/;
				var cardId = cardIdExtractRegex.exec(targetId)[1];
				var cardIds = [];
				cardIds.push(cardId);
				$.post('deleteCardsAssoc.do', {'cardIds': JSON.stringify(cardIds)}, function(data) {
					var ret = $.parseJSON(data);
					if (ret.success === true) {
						$('#card_'+cardId).remove();
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
				$('.card-dismiss').click(it.wallet.dismissCard);
			};

			$(function(){
				it.wallet.addHandlers();
				$('div.tabbable ul.nav li').removeClass('active');
				$('#cardTab').addClass('active');
			});
			
			
		</script>
		<style type="text/css">
			.card {
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
				<div class="span10" id="cardContainer" >
				 <ul class="thumbnails">
				    		<li class="span3" id="alert_<%=alert.getId()%>">
								<div class="thumbnail card"  >
								<span class="icon-trash icon-white pull-right alert-dismiss" id="alertDismiss_5"></span>
								<h3> alert.getMessage</h3>
								</div>
							</li>
				</ul>
				&nbsp;
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
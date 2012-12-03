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
		
		<%@include file="/common/pages/header.jsp" %>
		
		<script src="cards/js/card.js" > </script>
		<script type="text/javascript">

			$(function(){
				it.card.plotAll('${myOfferCardsJsonAttrKey}');
				it.card.discoverRefreshHandler();
				it.card.addHandlers();
				$('div.tabbable ul.nav li').removeClass('active');
				$('#cardTab').addClass('active');
			});
		</script>
		
		<style type="text/css">
			.card {
				background-color: #F5F5F5;
			}
			
			.bluishText {
				color: #0088CC;
			}
			
		</style>
	</head>
	<body>
	
		<%@include file="/common/pages/navHeader.jsp" %>
		<div class="container" >
			<div class="row">
				<div class="span2 " >
					<%@include file="/common/pages/featureTabs.jsp" %>
				</div>
				<div class="span10" >
					<h3 class="bluishText">My Cards</h3>
					<br/>
					<ul id="myCardsContainer" class="thumbnails">
					</ul>
				</div>
			</div>
			<hr class="span10 offset1" >
			<div class="row">
				<div class="span10 offset2" >
					<div class="row" >
						<h3 class="bluishText">Discover your Cards</h3>
						<br/>
					</div>
					
					<div class="row span10">
						<input id="cardFullName" class="cardDetail span4" type="text" placeholder="Card Name (e.g. Citibank Platinum MasterCard)" />
						<ul id="cardsContainer" class="thumbnails">
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="/common/pages/footer.jsp" %>
	
	</body>
</html>
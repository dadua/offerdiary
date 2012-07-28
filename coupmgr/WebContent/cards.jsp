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
			it.card = it.card || {};
						
			it.card.dismiss = function(e) {
				var target = e.target;
				var targetId = target.id;
				var cardIdExtractRegex = /^cardDismiss_(.*)/;
				var cardId = cardIdExtractRegex.exec(targetId)[1],
				card = {id:cardId};
				var cardIds = [];
				cardIds.push(cardId);
				$.post('removeCardFromWallet.do', {'offerCardJson': JSON.stringify(card)}, function(data) {
					var ret = $.parseJSON(data);
					if (ret.success === true) {
						$('#card_'+cardId).remove();
					} else {
						//Handle error case
					}
				});
			};
			
			it.card.discoverRefreshHandler = function() {
				
				var refreshAddableCards = function(e) {
					var query = $(this).val();
					it.card.plotAddableCards(query);
				}
				
				$('#cardFullName').keyup(refreshAddableCards);
				
			}
			
			it.card.addHandlers = function () {
				$('.icon-trash').hover(function(e) {
					$(this).removeClass('icon-white');
				}, function(e){
					$(this).addClass('icon-white');
				});
				$('.card-dismiss').click(it.card.dismiss);
				this.discoverRefreshHandler();
			};
			
			it.card.appendCardTo = function(containerElemSelector, card) {
				
				var cardDisplayHtml = '<li class="span3" id="card_'+ card.id + '">';
				cardDisplayHtml += '<div class="thumbnail card">';
				cardDisplayHtml += '<span class="icon-trash icon-white pull-right card-dismiss" id="cardDismiss_'+card.id + '"></span>';
				cardDisplayHtml += '<h4> Card Name: </h4>'+card.name+'</div></li>';
				$(cardDisplayHtml).appendTo(containerElemSelector);
			}
			
			it.card.appendCardsTo = function(containerElemSelector, cards) {
				for (var i=0; i < cards.length; i++) {
					this.appendCardTo(containerElemSelector, cards[i]);
				}
			}
			
			it.card.plotMyCards = function(cards) {
				this.appendCardsTo('#myCardsContainer', cards);
			}
			
			it.card.plotAddableCards = function(query) {
				$.getJSON('searchOfferCards.do', {searchKey:query}, function(data){
					var cards = data.result;
					$('#cardsContainer').html('');
					it.card.appendCardsTo('#cardsContainer', cards);
				});
			}
			
			it.card.plotAll = function() {
				var myCards = JSON.parse('${myOfferCardsJsonAttrKey}');
				this.plotMyCards(myCards);
				this.plotAddableCards('');<%-- Empty String for default set of cards.. --%>
			}

			$(function(){
				it.card.plotAll();
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
	
		<%@include file="navHeader.jsp" %>
		<div class="container" >
			<div class="row">
				<div class="span2 " >
					<%@include file="walletTabs.jsp" %>
				</div>
				<a id="discoverCardBtn" class="btn btn-primary btn-large pull-right" >Discover cards</a>
				<div class="span10" >
					 <ul id="myCardsContainer" class="thumbnails">
					</ul>
				</div>
			</div>
			<hr class="span10 offset1" >
			<div class="row">
				<div class="span10 offset2" >
					<div class="row" >
						<form class="pull-right" >
							<label class="bluishText">Card type you own: </label>
							<input id="cardFullName" class="cardDetail span4" type="text" placeholder="Card Name (e.g. Citibank Platinum MasterCard)" />
						</form>
						<h4 class="bluishText">Discover your Cards</h4>
					</div>
					
					<div class="row">
						<ul id="cardsContainer" class="thumbnails">
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
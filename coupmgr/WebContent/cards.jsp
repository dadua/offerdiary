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
			
			it.card.autoCompleteInit = function() {
				var cache = {}, lastXhr;
				$('#cardFullName').autocomplete({
					minLength: 2,
					source: function( request, response ) {
						var term = request.term;
						if ( term in cache ) {
							response(cache[term]);
							return;
						}
						lastXhr = $.getJSON("searchOfferCards.do", {searchKey: term}, function( data, status, xhr ) {
							var names = [];
							for (var i=0;i<data.result.length;i++) {
								names.push(data.result[i].name);
							}
							cache[term] = names;
							if ( xhr === lastXhr ) {
								response(names);
							}
						});
					}
				});
			};
			
			it.card.addHandlers = function () {
				$('.icon-trash').hover(function(e) {
					$(this).removeClass('icon-white');
				}, function(e){
					$(this).addClass('icon-white');
				});
				$('.card-dismiss').click(it.card.dismiss);
				$('#addSomeDiv').click(function(){
					$('#cardsContainer ul').append('<li> <div> Some content</div></li>');
				});
			};

			$(function(){
				it.card.addHandlers();
				it.card.autoCompleteInit();
				$('div.tabbable ul.nav li').removeClass('active');
				$('#cardTab').addClass('active');
			});
			
			
		</script>
		<style type="text/css">
			.card {
				background-color: #F5F5F5;
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
				<div class="span7" id="cardContainer" >
					 <ul class="thumbnails">
			    		<li class="span3" id="card_5">
							<div class="thumbnail card"  >
							<span class="icon-trash icon-white pull-right card-dismiss" id="cardDismiss_5"></span>
							<h3> Card Name: Card Value</h3>
							</div>
						</li>
					</ul>
				</div>
				<div class="span3" >
					<div class="modal hide fade" id="addCardModal" style="display:none" >
						<div class="modal-header" >
							<a class="close" data-dismiss="modal">×</a>
							<h2>Add New Card</h2>
						</div>
						<div class="modal-body" >
							<form >
								<label>Card type you own: </label>
								<input id="cardFullName" class="cardDetail span4" type="text" placeholder="Card Name (e.g. Citibank Platinum MasterCard)" />
							</form>
							<button id="addSomeDiv" ></button>
							
							<div id="cardsContainer" >
								<ul>
								</ul>
							</div>
						</div>
						<div class="modal-footer" >
							<a href="#" class="btn" data-dismiss="modal">Cancel</a>
							<%--
							<a id="addCardToWallet" class="btn btn-primary">Add Card</a>
							 --%>
						</div>
					</div>
					<a id="addCardModalBtn" class="btn btn-primary btn-large" data-toggle="modal" href="#addCardModal" >Add New Card for offers</a>
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
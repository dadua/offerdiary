<%@include file="/common/pages/headBegin.jsp" %>
		<title>My Cards</title>
		
		<script src="cards/js/card.js" > </script>
		<script src="common/js/rowFluidAdder.jquery.js" charset="UTF-8"> </script>
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
        	<%@include file="/common/css/layout.css" %>
			<%@include file="/cards/css/cards.css" %>
			
			.bluishText {
				color: #0088CC;
			}
			
		</style>
<%@include file="/common/pages/bodyBegin.jsp" %>
	
    <div class="container" >
        <%@include file="/common/pages/featureTabsRow.jsp" %>
        <div class="row">
            <div id="filterOptionsContainer" class="span2 " >
            	<%@include file="/cards/pages/cardsFilters.html" %>
            </div>
            <div class="span10 content-container" >
                <div class="container-fluid">
                    <div class="row-fluid">
                        <h3 class="bluishText">My Cards</h3>
                    </div>
                    <br/>
                    <div class="row-fluid">
                        <div id="myCardsContainer" class="container-fluid">
                        </div>
                    </div>
                </div>
                
                <div class="container-fluid">
                    <div class="row-fluid">
                        <hr />
                    </div>
                </div>
                
                <div id="featureContainer" class="container-fluid">
                	
                	<%@include file="/cards/pages/discoverCardHeadingRow.html" %>

					<%@include file="/cards/pages/offerOnCardHeadingRow.html" %>
                    
                    <br/>
                    
                    <div class="row-fluid">
                        <div id="cardsContainer" class="container-fluid discoverCards">
                        </div>
                        <div id="offerOnCardContainer" class="container-fluid offerOnCard hide">
                        </div>
                    </div>
                    
                </div>
                
            </div>
        </div>

    </div>
    <div class="templates hide" style="display:none">
    
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
	

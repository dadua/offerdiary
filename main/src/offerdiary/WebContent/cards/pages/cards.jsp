<%@include file="/common/pages/headBegin.jsp" %>
    <title>My Cards</title>
    <script src="cards/js/card.js" > </script>
    <script src="offers/js/offer.js" > </script>
    <script src="cards/js/offers_card.js" > </script>
    <script src="offers/js/share_offers.js" > </script>
    <script src="offers/js/offerdetail.js" > </script>
    <script src="common/js/rowFluidAdder.jquery.js" charset="UTF-8"> </script>
    <script src="common/libs/jquery-pagination/jquery.pagination.js" charset="UTF-8"> </script>

    <script type="text/javascript">

        $(function(){
            $('#cardTab').addClass('active');
            it.card.plotAll('${myOfferCardsJsonAttrKey}');
            it.card.discoverRefreshHandler();
            it.card.setupCardFiltersHandlers();

		});
    </script>

    <style type="text/css">
        <%@include file="/common/css/layout.css" %>
        <%@include file="/cards/css/cards.css" %>
        <%@include file="/cards/css/eachCard.css" %>
        <%@include file="/offers/css/eachOffer.css" %>
        <%@include file="/common/libs/jquery-pagination/pagination.css" %>
        
    </style>

<%@include file="/common/pages/bodyBegin.jsp" %>
	
    <div class="container" >
    	<%@include file="/common/pages/actionInfoRow.jsp" %>
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
                        <div class="container-fluid">
	                        <div id="cardPaginationContainer" class="row-fluid span6 offset6">
		                    </div>
	                    </div>
                        
                    </div>
                    
                </div>
                
            </div>
        </div>

    </div>
    <%@include file="/offers/templates/shareOfferTemplate.html" %>
    <div class="templates hide" style="display:none">
        <%@include file="/cards/templates/eachCardTemplate.html" %>
        <%@include file="/offers/templates/eachOfferTemplate.html" %>
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
	

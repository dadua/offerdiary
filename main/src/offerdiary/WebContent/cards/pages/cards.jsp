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
            <div class="span2 " >
                <div class="container-fluid options-left-container">
                    <ul class="nav nav-list">
                        <li class="nav-header">Cards by:</li>
                        <li class="active"><a href="#">My Cards</a></li>
                        <li><a href="#">Discover new cards</a></li>
                        <li class="divider"></li>
                        <li class="nav-header">By Issuing Bank:</li>
                        <li><a href="#">CitiBank</a></li>
                        <li><a href="#">ICICI Bank</a></li>
                        <li><a href="#">HSBC Bank</a></li>
                    </ul>
                </div>
            </div>
            <div class="span10 content-container" >
                <div class="container-fluid">
                    <div class="row-fluid">
                        <h3 class="bluishText">My Cards</h3>
                    </div>
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
                <div class="container-fluid">
                    <div class="row-fluid" >
                        <div class="span6">
                            <h3 class="bluishText">Discover your Cards</h3>
                        </div>
                        <div class="span5 offset1">
                        	<br/>
	                        <div class="form-search">
	                            <div class="input-append">
	                                <input id="cardFullName" class="cardDetail search-query input-xlarge" type="text" placeholder="Card Name (e.g. Citibank Platinum MasterCard)" />
	                                <button class="btn"><i class="icon-search"></i></button>
	                            </div>
	                        </div>
	                    </div>
                    </div>
                    <br/>
                    <div class="row-fluid">
                        <div id="cardsContainer" class="container-fluid">
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
	

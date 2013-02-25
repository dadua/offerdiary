<%@include file="/common/pages/headBegin.jsp" %>

    <script src="offers/js/offer.js" > </script>
    <script src="offers/js/share_offers.js" > </script>
    
    <script type="text/javascript">
        $(function() {
            /*
            var offersJson = '${myOffersJson}',
                offers = JSON.parse(offersJson);
            it.offer.init(offers);
            it.offer.share.init();
            */
        });
            
    </script>

    <style type="text/css">
    
            <%@include file="/common/css/layout.css" %>
            <%@include file="/offers/css/offer.css" %>

    </style>

    <title>Offers</title>
    
<%@include file="/common/pages/bodyBegin.jsp" %>

    <div class="container" >
    	<%@include file="/common/pages/actionInfoRow.jsp" %>
        <div class="row">
            <div class="content-container"  >
                <div class="container-fluid">
                    <div class="row-fluid">
                    </div>
                    <div class="row-fluid">
                        <div id="offerContainerFluid" class="container-fluid">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


	<%@include file="/offers/templates/shareOfferTemplate.html" %>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

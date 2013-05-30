<%@include file="/common/pages/headBegin.jsp" %>

    <script src="offers/js/offer.js" > </script>
    <script src="offers/js/offerdetail.js" > </script>
    <script src="offers/js/share_offers.js" > </script>
    
    <script type="text/javascript">
        $(function() {
            
            var offerJson = '${offerJson}',
            	offer = JSON.parse(offerJson);

            it.offer.detail.init(offer, ${isSharedOffer});
        });
            
    </script>

    <style type="text/css">
    
            <%@include file="/common/css/layout.css" %>
            <%@include file="/offers/css/eachOffer.css" %>
            <%@include file="/offers/css/offer.css" %>
            .largeTitleFontSize {
			    font-size: 1.4em;
			}
            

    </style>

    <title>Offer - ${offer.description}</title>
    
<%@include file="/common/pages/bodyBegin.jsp" %>

    <div class="container" >
        <div class="row">
            <div class=""  >
                <div class="container-fluid">
                    <div class="row-fluid bluishText largeTitleFontSize offset1">
                    	<strong>
                    	Shared Offer 
                    	</strong>
                    </div>
                    <hr class="offset1 span8" />
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <div class="row-fluid">
                        <div id="offerContainerFluid" class="container-fluid">
                        ${offerHtml}                        
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <%@include file="/offers/templates/shareOfferTemplate.html" %>
    <div class="templates hide" >
        <%@include file="/offers/templates/offerDetailTemplate.html" %>
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

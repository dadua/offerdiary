<%@include file="/common/pages/headBegin.jsp" %>

    <script src="offers/js/offer.js" > </script>
    <script src="offers/js/offerdetail.js" > </script>
    <script src="offers/js/share_offers.js" > </script>
    
    <script type="text/javascript">
        $(function() {
            
            var offerJson = '${offerJson}',
            	offer = JSON.parse(offerJson);

            it.offer.detail.init(offer);
        });
            
    </script>

    <style type="text/css">
    
            <%@include file="/common/css/layout.css" %>
            <%@include file="/offers/css/offer.css" %>
            <%@include file="/offers/css/eachOffer.css" %>
            .largeTitleFontSize {
			    font-size: 1.4em;
			}
            

    </style>

    <title>Offer Details</title>
    
<%@include file="/common/pages/bodyBegin.jsp" %>
    <div class="container" >
        <div class="row">
            <div class=""  >
                <div class="container-fluid">
                    <div class="row-fluid bluishText largeTitleFontSize offset1">
                    	<strong>
                    	Offer Detail
                    	</strong>
                    </div>
                    <hr class="offset1 span8" />
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    
                    <div class="row-fluid">
                        <div id="offerContainerFluid" class="container-fluid">
                            <div id="offer_${offer.id}" class="thumbnail offerBlock box-shadow span7 offset2">
                                <div class="row-fluid expiryBlock">
                                    <div class="daysToExpire label hide">
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6">
                                        <div class="vendorImgSection">
                                            <img class="targetVendor-logoUrl" 
                                            src="images/stores/defaultVendor.jpg"
                                            alt="${offer.targetVendor.name}">

                                            <div class="vendorOnOfferNameVal hide">
                                                ${offer.targetVendor.name}
                                            </div>
                                        </div>
                                        <span class="offerCode sans-serif-extra-small span8 offset2">Code: <span class="offerCodeVal">${offer.offerCode}</span></span>
                                    </div>
                                    <div class="span6">
                                        <div class="row-fluid offer-icon" >
                                            <div class="span11" style="min-height:0">
                                                <div class="pull-right">
                                                    <a href="#" onClick="" >
                                                        <i class="icon-share offerShare" id="offerShare_${offer.id}" title="Share Offer"></i>
                                                    </a>
                                                    <a class="" href="#" class="" onClick="" >
                                                        <i class="icon-trash offerTrash" id="offerTrash_${offer.id}" title="Trash Offer"></i>
                                                    </a>
                                                    <a class="" href="#" onClick="" >
                                                        <i class="icon-plus offerAdd" title="Add this offer to OD"></i>
                                                    </a>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="row-fluid margin-zero">
                                            <span class="offerDesc">
                                                ${offer.description}
                                            </span>
                                            <a href="#" class="offerUrl">
                                            </a>
                                        </div>
                                        <div class="row-fluid margin-zero">
                                            <span class="sans-serif-small offerBold">By:</span>
                                            <span class="vendorName sans-serif-small">
                                                ${offer.targetVendor.name}
                                            </span>
                                        </div>
                                        <div class="row-fluid margin-zero">
                                            <span class="sans-serif-small">
                                                <a class="vendorUrl" 
                                                    href="${offer.targetVendor.siteUrl}"
                                                    target="_blank">
                                                    ${offer.targetVendor.siteUrl}
                                                </a>
                                            </span>
                                        </div>
                                        <div class="row-fluid margin-zero expiryDate">
                                            <span class="sans-serif-small">
                                                Expire
                                            </span>
                                            <i class="icon-calendar"></i>
                                            <span class="offerExpiryDate sans-serif-extra-small">
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="offerSourceContainer pull-right hide">
                                        <span class="sans-serif-small offerSourceName">Source:</span>
                                        <span class="offerSourceVal sans-serif-extra-small"></span>
                                    </div>
                                </div>
                                <fb:comments id="oDfferdFbComment" href="http://offerdiary.com/" width="550" num_posts="10"></fb:comments>
                            </div>
                            <!-- End of Offer Template UI --!>

                            
                        
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

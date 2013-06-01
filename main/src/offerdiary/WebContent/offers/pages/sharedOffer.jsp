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
          <div class="offer_detail_ui_template thumbnail offerBlock box-shadow span7 offset2">
              <div class="row-fluid expiryBlock">
                  <div class="daysToExpire label hide">
                  </div>
              </div>
              <div class="row-fluid">

                  <div class="span6">
                      <div class="vendorImgSection">
                          <img class="targetVendor-logoUrl" alt="Vendor" src="${vendorLogoUrl}">
                          <div class="vendorOnOfferNameVal hide"></div>
                      </div>
                      <span class="offerCode sans-serif-extra-small span8 offset2">Code: <span class="offerCodeVal"></span></span>
                  </div>
                  <div class="span6">
                      <div class="row-fluid offer-icon" >
                          <div class="span11" style="min-height:0">
                              <div class="pull-right">
                              </div>
                          </div>
                      </div>
                      <div class="row-fluid margin-zero">
                          <span class="offerDesc"> </span>
                          <a href="#" class="offerUrl">
                          </a>
                      </div>
                      <div class="row-fluid margin-zero">
                          <span class="sans-serif-small offerBold">By:</span>
                          <span class="vendorName sans-serif-small">
                          </span>
                      </div>
                      <div class="row-fluid margin-zero">
                          <span class="sans-serif-small">
                              <a class="vendorUrl" 
                                  target="_blank">
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
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

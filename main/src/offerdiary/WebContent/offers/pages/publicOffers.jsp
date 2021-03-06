<%@include file="/common/pages/headBegin.jsp" %>

    <script src="common/js/rowFluidAdder.jquery.js" charset="UTF-8"> </script>
    <script src="offers/js/offer.js" > </script>
    <script src="offers/js/public_offers.js" > </script>
    <script src="offers/js/offerdetail.js" > </script>
    <script src="offers/js/share_offers.js" > </script>
    <script src="common/libs/jquery-pagination/jquery.pagination.js" charset="UTF-8"> </script>
	<script src="common/libs/history.js/scripts/bundled/html4+html5/jquery.history.js"></script>
    
    <script type="text/javascript">
        $(function() {
            $('#publicOfferTab').addClass('active');
            var publicOffersCount = ${publicOffersCount};
            it.publicOffers.init(publicOffersCount);
        });
            
    </script>

    <style type="text/css">
    
            <%@include file="/common/css/layout.css" %>
            <%@include file="/offers/css/vendor.css" %>
            <%@include file="/offers/css/offer.css" %>
            <%@include file="/offers/css/eachOffer.css" %>
            <%@include file="/common/libs/jquery-pagination/pagination.css" %>
            

    </style>

    <title>Discover Great Coupons, Deals and Offers</title>
    
<%@include file="/common/pages/bodyBegin.jsp" %>

    <div class="container" >
        <%@include file="/common/pages/featureTabsRow.jsp" %>
        <div class="row">
        <%--
            <div class="span2" >
                <div class="span2 container-fluid options-left-container">
                    <ul class="nav nav-list offerFilters">
                        <li class="nav-header">My Offers:</li>
                        <li class="active validOffers"><a href="#">Valid</a></li>
                        <li class="expiredOffers"><a href="#">Expired</a></li>
                        <li class="allOffers"><a href="#">All</a></li>
                        <li class="divider"></li>
                        <li class="nav-header">Expiry in:</li>
                        <li class="expires7daysOffers"><a href="#">Expires in 7days</a></li>
                        <li class="expires1monthOffers"><a href="#">Expires in 1 month</a></li>
                        <li class="divider"></li>
                        <li class="nav-header">Added in:</li>
                        <li class="addedLastweekOffers"><a href="#">Added in the last week</a></li>
                        <li class="addedLastmonthOffers"><a href="#">Added in the last month</a></li>
                    </ul>
                </div>
            </div>
 --%>
            <div class="content-container"  >
                <div class="container-fluid">
                    <div class="row-fluid">
                    	<div class="span6">
	                    	<h3 class="bluishText">Discover New Offers</h3>
                    	</div>
                    	<div class="span5">
	                        <br/>
	                        <div class="form-search">
	                            <div class="input-append">
	                                <input id="searchOfferQuery" class="offerDetail search-query input-xlarge" type="text" value="${publicSearchQuery}" placeholder="Search Offers" />
	                                <button class="btn"><i class="icon-search"></i></button>
	                            </div>
	                        </div>
                        </div>
                    </div>
                    <br/>
                    <br/>

                    <div class="row-fluid">
                        <div id="offerContainerFluid" class="container-fluid">
                        </div>
                        <div class="container-fluid">
	                        <div id="offerPaginationContainer" class="row-fluid span6 offset6">
	                        </div>
	                    </div>
                    </div>
                    

                </div>
            </div>
        </div>
    </div>


    <div id="addOfferWizardRoot"></div>
	<%@include file="/offers/templates/shareOfferTemplate.html" %>
    <div id="templates" class="hide">
        <%@include file="/offers/templates/eachOfferTemplate.html" %>
        <div id="vendorSelectionTemplate">
            <div class="container-fluid" >
                <div class="form-search row-fluid">
                    <div class="input-append">
                        <input id="vendorQuery" class="offerDetail search-query input-large" type="text" placeholder="Vendor name" />
                        <button class="btn"><i class="icon-search"></i></button>
                    </div>
                </div>
                <br/>
                <div id="searchedVendors" class="row-fluid">
                    <ul class="thumbnails row-fluid" >
                        <li class="span4 vendor">
                            <div class="vendorImage thumbnail">
                                <img src="images/stores/defaultVendor.jpg" />
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="row-fluid">
					<div class="pull-right vendorSelectHelp">
					* Click on vendor to select and proceed
					</div>
				</div>
            </div>
        </div>
        <div id="benefitDetailsTemplate">
            <div class="form-horizontal">
                <div class="control-group">
                    <label class="control-label bluishText" for="offerCode">Offer Code:</label>
                    <div class="controls">
                        <input id="offerCode" type="text" placeholder="Offer Code" />
                    </div>
                </div>
                
                 <div class="control-group">
                    <label class="control-label bluishText" for="offerUrl">Offer Link:</label>
                    <div class="controls">
                        <input id="offerUrl" placeholder="Offer Link" type="text"></input>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label bluishText" for="offerDescription">Offer Description:</label>
                    <div class="controls">
                        <textarea id="offerDescription" rows="3" placeholder="Offer Description" ></textarea>
                    </div>
                </div>
            </div>
        </div>
        <div id="reminderDetailsTemplate">
	        <label class="radio">
	        	<input type="radio" name="optionsRadios" id="expiryOptionRadio" value="expiry" checked>
	            <div class="control-group">
	                <label class="control-label bluishText" for="expiryDateInput">Offer expires on:</label>
	                <div class="controls">
	                    <input id="expiryDateInput" type="text" placeholder="Example: 26 December, 2013" />
	                    <div id="expiryDatePicker"></div>
	                </div>
	            </div>
            </label>
            
        	<label class="radio bluishText">
	        	<input type="radio" name="optionsRadios" id="noExpiryOptionRadio" value="noExpiry" >
	        	Offer has no expiry
	        </label>
        </div>
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

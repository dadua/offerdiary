<%@include file="/common/pages/headBegin.jsp" %>

        <script src="common/js/wizard.js" charset="UTF-8"> </script>
        <script src="offers/js/addoffer_wizard.js" > </script>
        <script src="offers/js/vendor.js" > </script>
        <script src="offers/js/offer.js" > </script>
        
        <script type="text/javascript">
            $(function() {
                var offersJson = '${myOffersJson}',
                    offers = JSON.parse(offersJson);
                it.offer.appendOffers(offers, true);
                it.offer.addHandlers();
                $('#expiryDate').datepicker();
                it.offer.addwizard.init();
                $('#addOfferWizardBtn').click(it.offer.addwizard.getWizard().show);
                $('#addOfferModalBtn').click(it.offer.clearOfferFormVals);
            });
                
        </script>

        <style type="text/css">
        
        	<%@include file="/common/css/wizard.css" %>
        	<%@include file="/common/css/layout.css" %>
        	<%@include file="/offers/css/vendor.css" %>
        	
        	
        	<%@include file="/offers/css/offer.css" %>

        	<%@include file="/offers/css/eachOffer.css" %>

        </style>

        <title>Offers</title>
        
<%@include file="/common/pages/bodyBegin.jsp" %>

        <div class="container" >
	        <%@include file="/common/pages/featureTabsRow.jsp" %>
            <div class="row">
                <div class="span2" >
                	<div class="container-fluid options-left-container">
	                	<ul class="nav nav-list">
			                <li class="nav-header">Offers by:</li>
							<li class="active"><a href="#">All</a></li>
							<li><a href="#">Available</a></li>
							<li><a href="#">Expired</a></li>
							<li class="divider"></li>
							<li class="nav-header">Expiry based filters:</li>
							<li><a href="#"></a></li>
							<li><a href="#">Expires in 7days</a></li>
							<li class="nav-header">Addition time based:</li>
							<li><a href="#">Added in the last week</a></li>
							<li><a href="#">Added in the last month</a></li>
						</ul>
					</div>
                </div>
                
                <div class="span10 content-container"  >
                	<div class="row-fluid">
		                <div class="form-search span5 offset6">
	                        <div class="input-append">
	                            <input id="vendorQuery" class="offerDetail search-query input-xlarge" type="text" placeholder="Search Offers" />
	                            <button class="btn"><i class="icon-search"></i></button>
	                        </div>
	                    </div>
                    </div>
                    <br>
                    
               		<div class="row-fluid">
                         <ul id="offerContainer" class="thumbnails ">
	                         <li class="span5 thumbnail offerBlock addOfferBlock" style="min-height: 100px; margin-left:2.5641%;">
	                         	<br>
	                         	<br>
	                         	<div class="row-fluid">
	                         		<div class="span6 offset3">
		                         		<a id="addOfferWizardBtn" class="btn btn-success btn-large" href="#" >Add New Offer</a>
		                         	</div>
                                 </div>
                                 <br>
                             </li>
	                    </ul>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="/offers/templates/eachOfferTemplate.html" %>
		<div id="addOfferWizardRoot"></div>
        <div id="templates" class="hide">
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
                        <label class="control-label bluishText" for="offerDescription">Offer Description:</label>
                        <div class="controls">
                            <textarea id="offerDescription" rows="3" placeholder="Offer Description" ></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div id="reminderDetailsTemplate">
                <div class="control-group">
                    <label class="control-label bluishText" for="expiryDateInput">Expiry Date:</label>
                    <div class="controls">
                        <input id="expiryDateInput" type="text" placeholder="Example: 16 December, 2013" />
                        <div id="expiryDatePicker"></div>
                    </div>
                </div>
            </div>
        </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

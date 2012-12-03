<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">

        <%@include file="/common/pages/header.jsp" %>

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
        	<%@include file="/offers/css/vendor.css" %>
        	
        	<%@include file="/offers/css/offer.css" %>

            .ui-autocomplete-loading {
                background: white url('images/ui-anim_basic_16x16.gif') right center no-repeat;
            }

        	<%@include file="/offers/css/eachOffer.css" %>

        </style>

        <title>Offer Wallet</title>
    </head>
    <body>

        <%@include file="/common/pages/navHeader.jsp" %>

        <div class="container" >
            <div class="row">
                <div class="span2" >
                    <%@include file="/common/pages/featureTabs.jsp" %>
                </div>
                <div class="span8"  >
                    <ul id="offerContainer" class="thumbnails row">
                    </ul>
                </div>
                <div class="span2" >
	                <a id="addOfferWizardBtn" class="btn btn-primary btn-large" href="#" >Add Offer to Wallet</a>
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

        <%@include file="/common/pages/footer.jsp" %>

    </body>
</html>

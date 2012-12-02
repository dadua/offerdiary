<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">

        <%@include file="/common/pages/header.jsp" %>

        <script src="js/it/wizard.js" charset="UTF-8"> </script>
        <script src="js/it/addoffer_wizard.js" > </script>
        <script src="js/it/vendor.js" > </script>
        <script src="js/it/offer.js" > </script>
        
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
            /* Add offer related classes */
            .popover {
                z-index: 1200;
            }

            .selected {
                background-color: #ADFF2F;
                position: relative;
            }

            .selected-tick {
                background: none repeat scroll 0 0 #5EF118;
                border-radius: 23px 23px 23px 23px;
                font-size: 11px;
                font-weight: 100;
                color:white;
                line-height: 22px;
                opacity: 0.7;
                position: absolute;
                text-align: center;
                top: -10%;
                left: 90%;
                width: 22px;
            }

            .vendorImage {
                cursor: pointer;
            }

            .selected-tick:hover {
                color: white;
                text-decoration: none;
            }

            .offer {
                background-color: #F5F5F5;
            }

            .daysToExpire {
                margin-left: 48%;
                margin-right:10%;
                padding: 5px;
            }

            .checkBoxSelected {
                color: green;
            }

            .checkBoxSelected > .icon-ok {
                background-color: green;
            }

            .checkBoxUnSelected {
                color: #333333;
            }

            .checkBoxUnSelected > .icon-ok {
                background-color: transparent;
            }

            .ui-autocomplete-loading {
                background: white url('images/ui-anim_basic_16x16.gif') right center no-repeat;
            }

            /*TODO: this is a dependecy of eachOfferTemplate.html*/
            .offerBlock{
                width: 325px;
                height: 125px; 
                position:relative;
            }

            .space15{
                width: 140px;
                float: left;
            }

            .offerDesc {
                font-family:  sans-serif;
                font-size: 20px;
                font-style: normal;
                font-weight: normal;
                text-transform: normal;
                line-height: 1.2em;
            }

            .sans-serif-normal{
                font-family:  sans-serif;
                font-size: 14px;
            }

            .sans-serif-small{
                font-family:  sans-serif;
                font-size: 12px;
            }

            .sans-serif-extra-small{
                font-family:  sans-serif;
                font-size: 10px;
            }

            .shadow {
                -moz-box-shadow:    3px 3px 5px 6px #ccc;
                -webkit-box-shadow: 3px 3px 5px 6px #ccc;
                box-shadow:         3px 3px 5px 6px #ccc;
            }

            .margin-left-10{
                margin-left: 10px;
            }
            .offer-icon{
                margin-left:-5px;

            }
            .margin-zero{
                margin-left: 0px;
            }

            /*TODO: this is a dependecy of wizard.js*/
            .blueColor {
                color:#3A87AD;
            }

            .bluishText {
                color: #0088CC;
            }
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

<%@page import="com.itech.offer.model.Offer"%>
<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%>

<%@page import="java.util.List"%>
<%@page import="com.itech.offer.model.Offer"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Offer Wallet</title>

        <%@include file="common/header.jsp" %>

        <script src="js/it/wizard.js" charset="UTF-8"> </script>
        <script src="js/it/addoffer_wizard.js" > </script>
        <script src="js/it/vendor.js" > </script>
        <script src="js/it/offer.js" > </script>
        <script type="text/javascript">
            $(function() {
                $('#addOfferToWallet').click(it.offer.addOffer);
                var offersJson = '${myOffersJson}',
                    offers = JSON.parse(offersJson);
                it.offer.appendOffers(offers, true);
                it.offer.addHandlers();
                $('#expiryDate').datepicker();
                it.vendor.JqUiAutoCompleteInit();
                it.offer.addwizard.init();
                $('#addOfferWizardBtn').click (it.offer.addwizard.getWizard().show);
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

            .offerNum{
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
    </head>
    <body>

        <%@include file="common/navHeader.jsp" %>

        <div class="container" >
            <div class="row">
                <div class="span2" >
                    <%@include file="walletTabs.jsp" %>
                </div>
                <div class="span8"  >
                    <ul id="offerContainer" class="thumbnails row">
                    </ul>
                </div>
                <div class="span2" >
                    <div class="modal hide fade" id="addOfferModal" style="display:none" >
                        <div class="modal-header" >
                            <a class="close" data-dismiss="modal">×</a>
                            <h2>Add a offer to wallet </h2>
                        </div>
                        <div class="modal-body" >
                            <form >
                                <label>Code: </label>
                                <input id="code" type="text" class="span3 offerDetail" placeholder="Offer Code?" />
                                <label>Discount Description: </label>
                                <textarea class="span3 OfferDetail" id="discountDetails" placeholder="Discount Details"></textarea>
                                <label> Expiry Date: </label>
                                <input id="expiryDate"  class="offerDetail" placeholder="Expiry Date(mm/dd/yyyy)"/>
                                <label> Vendor: </label>
                                <input id="vendor" class="offerDetail" type="text" placeholder="Vendors" />
                                <label>Notifications Config: </label>
                                <div id="notificationConfig" class="btn-group" data-toggle="buttons-checkbox" >
                                    <a id="emailNotify" class="btn active checkBoxSelected">
                                        <i class="icon-envelope"></i>
                                        Email
                                        <i class="icon-ok icon-white" ></i>
                                    </a>
                                    <a id="fbNotify" class="btn checkBoxUnSelected">
                                        Fb <i class="icon-ok icon-white"></i>
                                    </a>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer" >
                            <a href="#" class="btn" data-dismiss="modal">Cancel</a>
                            <a id="addOfferToWallet" class="btn btn-primary">Add Offer</a>
                        </div>
                    </div>
                    <a id="addOfferModalBtn" class="btn btn-primary btn-large" data-toggle="modal" href="#addOfferModal" >Add Offer to Wallet</a>
                    <a id="addOfferWizardBtn" class="btn btn-primary btn-large" href="#" >Add Offer to Wallet wizard</a>

                </div>
            </div>
        </div>
        <%@include file="eachOfferTemplate.html" %>
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
                        <label class="control-label bluishText" for="couponCode">Coupon Code:</label>
                        <div class="controls">
                            <input id="couponCode" type="text" placeholder="Coupon Code" />
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label bluishText" for="offerDescription">Offer Description:</label>
                        <div class="controls">
                            <input id="description" type="text" placeholder="Offer Description" />
                        </div>
                    </div>

                </div>
            </div>
            <div id="reminderDetailsTemplate">
            </div>
        </div>

        <%@include file="common/footer.jsp" %>

    </body>
</html>

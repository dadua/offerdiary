<%@page import="com.itech.offer.model.Offer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@page import="java.util.List"%>
<%@page import="com.itech.offer.model.Offer"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>Offer Wallet</title>
		
		<%@include file="commonHeader.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			it.wallet = it.wallet || {};
			
			it.wallet.appendOffers = function(offers, isOldAddition) {
				for (var i=0; i<offers.length;i++) {
					this.appendOffer(offers[i], isOldAddition);
				}
			};
			
			
			it.wallet.getDaysToExpire = function(offer) {
				var expiryMillisUtc = offer.expiryDateInMillis;
				var d = new Date();
				var currentMillisUtc = d.getTime();
				var millisToExpire = (expiryMillisUtc - currentMillisUtc);
				var daysToExpire = Math.floor(millisToExpire/(1000*60*60*24));
				return daysToExpire;
			}
			
			it.wallet.getExpiryDateBasedClass = function(daysToExpire) {
				if(daysToExpire < 0 ) {
					return '';
				} else if(daysToExpire < 14) {
					return 'label-important';
				} else if (daysToExpire < 30) {
					return 'label-warning';
				} else if (daysToExpire < 90) {
					return 'label-info';
				} else if (daysToExpire < 120) {
					return 'label-inverse';
				} else {
					return 'label-success';
				}
			}
			
			it.wallet.getFormattedDaysToExpire = function (daysToExpire) {
				return daysToExpire + (daysToExpire==1?' day':' days')+' to expire';
			}
			
			it.wallet.getFormattedMonthsToExpire = function(daysToExpire) {
				var monthsToExpire = Math.floor(daysToExpire/30);
				return monthsToExpire + (monthsToExpire==1?' month, ':' months, ') + this.getFormattedDaysToExpire(Math.floor(daysToExpire%30));
			}
			
			it.wallet.getFormattedYearsToExpire = function(daysToExpire) {
				var yearsToExpire = Math.floor(daysToExpire/365);
				return yearsToExpire + (yearsToExpire==1?' year, ':' years, ') + this.getFormattedMonthsToExpire(Math.floor(daysToExpire%365));
			}
			
			it.wallet.getFormattedTimeToExpire = function(daysToExpire) {
				if (daysToExpire < 30) {
					return this.getFormattedDaysToExpire(daysToExpire);
				} else if (daysToExpire < 365) {
					return this.getFormattedMonthsToExpire(daysToExpire);
				} else {
					return this.getFormattedYearsToExpire(daysToExpire);
				}
			};
			
			it.wallet.getCompactFormattedTimeToExpire = function(timeToExpireString) {
				if (timeToExpireString.length > 16) {
					var displayedTimeToExpire = timeToExpireString.substring(0, 12),
					undisplayedTimeToExpire = timeToExpireString.substring(16, timeToExpireString.length);
					displayedTimeToExpire += '<span class="hiddenTillAddSuccess" style="display:none" >';
					displayedTimeToExpire += timeToExpireString.substring(12,16);
					displayedTimeToExpire += '</span>';
					displayedTimeToExpire += '<span style="display:none" class="hiddenPartOfTime">';
					displayedTimeToExpire += undisplayedTimeToExpire;
					displayedTimeToExpire += '</span>';
					displayedTimeToExpire += '<a class="daysToExpireDetails" href="#" title="'+timeToExpireString +'">...</a>';
					return displayedTimeToExpire;
				} else {
					return timeToExpireString;
				}
			}
			
			it.wallet.appendOffer = function (offer, isOldAddition) {
				
				var daysToExpire = this.getDaysToExpire(offer);
				var labelClass = this.getExpiryDateBasedClass(daysToExpire);
				var daysToExpireHtml = '<div class="daysToExpire label '+ labelClass + '" id="offerExpire_'+ offer.id + '">';
				var formattedTimeToExpire = this.getFormattedTimeToExpire(daysToExpire);
				if (daysToExpire < 0) {
					daysToExpireHtml += 'Offer Expired!!';
				} else {
					var compactFormattedTime = this.getCompactFormattedTimeToExpire(formattedTimeToExpire);
					daysToExpireHtml += compactFormattedTime;
				}
				daysToExpireHtml += '</div>';
	    		var offerHtml = '<li class="span3" id="offer_';
	    		offerHtml += offer.id;
	    		offerHtml += '" >';
	    		offerHtml += '<span class="addingDoneLabel label label-success">Done!</span>';
	    		offerHtml += daysToExpireHtml;
	    		offerHtml += '<div class="thumbnail"><span class="icon-trash icon-white pull-right offer-trash" title="Trash Me" id="offerTrash_';
	    		offerHtml += offer.id;
	    		offerHtml += '" ></span><h3>Details:';
	    		offerHtml += offer.description;
	    		offerHtml += '</h3><h5>Code:';
	    		offerHtml += offer.offerCode;
	    		offerHtml += '</h5><h5>Discount:';
	    		offerHtml += offer.discountValue;
	    		offerHtml +=  '</h5></div></li>';
	    		$(offerHtml).appendTo('.thumbnails');
	    		
	    		/*
	    		$('#offerExpire_'+offer.id).position({
	    				my: 'center top',
	    				at: 'center top',
	    				of: '#offer_' + offer.id,
	    				offset: '0 0'
				});
	    		*/
	    		$('#offer_'+offer.id + ' .thumbnail').addClass('offer', 9000);
	    		if (isOldAddition) {
	    			$('#offer_'+offer.id+ ' span.addingDoneLabel').hide();
	    			$('.hiddenTillAddSuccess').show();
	    		} else {
	    			$('#offer_'+offer.id+ ' span.addingDoneLabel').hide('highlight', 9000, function(){
		    			$(this).parent().find('span.hiddenTillAddSuccess').show();
		    			$(this).remove();
		    		});
	    		}
			};
			
			it.wallet.clearofferormVals = function () {
				$('.offerDetail').val('');
				$('#emailNotify').addClass('active');
				$('#fbNotify').removeClass('active');
			};
			
			
			it.wallet.addOffer = function () {
				var code = $('#code').val(),
				expiryDateInMillis = $('#expiryDate').datepicker('getDate').getTime(),
				detail = $('#discountDetails').val(),
				emailNotify = $('#emailNotify').hasClass('active'),
				fbNotify = $('#fbNotify').hasClass('active'),
				notifyVO = {
					emailNotify: emailNotify,
					fbNotify: fbNotify
					//Will include time to notify etc. here
				},
				offer = {
					offerCode: code,
					description: detail,
					expiryDateInMillis: expiryDateInMillis,
					notifyVO: notifyVO
				},		
				offers = [];
				offers.push(offer);
				$.post('saveOffers.do',
						{'offers': JSON.stringify(offers)},
						function (data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								it.wallet.appendOffers(ret.result);
								it.wallet.addHandlers();
							} else {
								//Handle error case
							}
							$('#addOfferModal').modal('hide');
						});
			};
			
			it.wallet.trashOffer = function(e) {
				var target = e.target;
				var targetId = target.id;
				var offerIdExtractRegex = /^offerTrash_(.*)/;
				var offerId = offerIdExtractRegex.exec(targetId)[1];
				var offerIds = [];
				offerIds.push(offerId);
				$.post('deleteOffers.do', {'offerIds': JSON.stringify(offerIds)}, function(data) {
					var ret = $.parseJSON(data);
					if (ret.success === true) {
						$('#'+targetId).tooltip('hide');
						$('#offer_'+offerId).remove();
					} else {
						//Handle error case
					}
				});
			};
			
			it.wallet.addHandlers = function () {
				
				$('.icon-trash').hover(function(e) {
					$(this).removeClass('icon-white');
				}, function(e){
					$(this).addClass('icon-white');
				});
				$('.offer-trash').click(it.wallet.trashOffer).tooltip();
				$('.daysToExpireDetails').tooltip({
					placement: 'right'
				}).click(function(e){
					$(this).parent().find('.hiddenPartOfTime').show();
					$(this).tooltip('hide').remove();
				});
			};
			
			it.wallet.vendorAutoCompleteInit = function() {
				var cache = {}, lastXhr;
				$('#vendor').autocomplete({
					minLength: 2,
					source: function( request, response ) {
						var term = request.term;
						if ( term in cache ) {
							response( cache[term] );
							return;
						}
						lastXhr = $.getJSON( "searchVendor.do", {searchKey: term}, function( data, status, xhr ) {
							var names = [];
							for (var i=0;i<data.result.length;i++) {
								names.push(data.result[i].name);
							}
							cache[term] = names;
							if ( xhr === lastXhr ) {
								response(names);
							}
						});
					}
				});
			}
			
			$(function() {
				$('#addOfferToWallet').click(it.wallet.addOffer);
				var offersJson = '${myOffersJson}',
				offers = JSON.parse(offersJson);
				it.wallet.appendOffers(offers, true);
				it.wallet.addHandlers();
				$('#expiryDate').datepicker();
				it.wallet.vendorAutoCompleteInit();
					
				$('#addOfferModalBtn').click(it.wallet.clearOfferFormVals);
				$('.checkBoxSelected').live('click', function() {
					$(this).removeClass('checkBoxSelected').addClass('checkBoxUnSelected');
				});
				$('.checkBoxUnSelected').live('click', function(){
					$(this).removeClass('checkBoxUnSelected').addClass('checkBoxSelected');
				});
			});
			
		</script>
		<style type="text/css">
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
			
		</style>
	</head>
	<body>
	
		<%@include file="navHeader.jsp" %>
		
		<div class="container" >
			<div class="row-fluid">
				<div class="span2" >
					<%@include file="walletTabs.jsp" %>
				</div>
				<div class="span7" id="offerContainer" >
				 <ul class="thumbnails">
				</ul>
				&nbsp;
				</div>
				<div class="span3" >
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
						
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
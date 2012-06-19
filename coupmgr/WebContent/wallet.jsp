<%@page import="com.itech.offer.model.Offer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@page import="java.util.List"%>
<%@page import="com.itech.coupon.model.Coupon"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>Coupon Wallet</title>
		
		<%@include file="commonHeader.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			it.wallet = it.wallet || {};
			
			it.wallet.appendCoupons = function(coupons, isOldAddition) {
				for (var i=0; i<coupons.length;i++) {
					this.appendCoupon(coupons[i], isOldAddition);
				}
			};
			
			
			it.wallet.getDaysToExpire = function(coupon) {
				var expiryMillisUtc = coupon.expiryDateInMillis;
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
			
			it.wallet.appendCoupon = function (coupon, isOldAddition) {
				
				var daysToExpire = this.getDaysToExpire(coupon);
				var labelClass = this.getExpiryDateBasedClass(daysToExpire);
				var daysToExpireHtml = '<div class="daysToExpire label '+ labelClass + '" id="couponExpire_'+ coupon.id + '">';
				var formattedTimeToExpire = this.getFormattedTimeToExpire(daysToExpire);
				if (daysToExpire < 0) {
					daysToExpireHtml += 'Coupon Expired!!';
				} else {
					var compactFormattedTime = this.getCompactFormattedTimeToExpire(formattedTimeToExpire);
					daysToExpireHtml += compactFormattedTime;
				}
				daysToExpireHtml += '</div>';
	    		var couponHtml = '<li class="span3" id="coupon_';
	    		couponHtml += coupon.id;
	    		couponHtml += '" >';
	    		couponHtml += '<span class="addingDoneLabel label label-success">Done!</span>';
	    		couponHtml += daysToExpireHtml;
	    		couponHtml += '<div class="thumbnail"><span class="icon-trash icon-white pull-right coupon-trash" title="Trash Me" id="couponTrash_';
	    		couponHtml += coupon.id;
	    		couponHtml += '" ></span><h3>Details:';
	    		couponHtml += coupon.description;
	    		couponHtml += '</h3><h5>Code:';
	    		couponHtml += coupon.offerCode;
	    		couponHtml += '</h5><h5>Discount:';
	    		couponHtml += coupon.discountValue;
	    		couponHtml +=  '</h5></div></li>';
	    		$(couponHtml).appendTo('.thumbnails');
	    		
	    		/*
	    		$('#couponExpire_'+coupon.id).position({
	    				my: 'center top',
	    				at: 'center top',
	    				of: '#coupon_' + coupon.id,
	    				offset: '0 0'
				});
	    		*/
	    		$('#coupon_'+coupon.id + ' .thumbnail').addClass('coupon', 9000);
	    		if (isOldAddition) {
	    			$('#coupon_'+coupon.id+ ' span.addingDoneLabel').hide();
	    		} else {
	    			$('#coupon_'+coupon.id+ ' span.addingDoneLabel').hide('highlight', 9000, function(){
		    			$(this).parent().find('span.hiddenTillAddSuccess').show();
		    			$(this).remove();
		    		});
	    		}
			};
			
			it.wallet.clearCouponFormVals = function () {
				$('.couponDetail').val('');
				$('#emailNotify').addClass('active');
				$('#fbNotify').removeClass('active');
			};
			
			
			it.wallet.addCoupon = function () {
				var code = $('#code').val(),
				expiryDateInMillis = $('#expiryDate').datepicker('getDate').getTime(),
				detail = $('#discountDetails').val(),
				emailNotify = $('#emailNotify').hasClass('active'),
				fbNotify = $('#fbNotify').hasClass('active'),
				notifyConfig = {
					emailNotify: emailNotify,
					fbNotify: fbNotify
					//Will include time to notify etc. here
				},
				coupon = {
					offerCode: code,
					description: detail,
					expiryDateInMillis: expiryDateInMillis,
				},
				offerNotificationConfig = {
					offer: coupon,
					notifyConfig: notifyConfig
				},
				couponNotificationConfigs = [];
				couponNotificationConfigs.push(offerNotificationConfig);
				$.post('saveCoupons.do',
						{'offerNotificationConfigs': JSON.stringify(couponNotificationConfigs)},
						function (data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								it.wallet.appendCoupons(ret.result);
								it.wallet.addHandlers();
							} else {
								//Handle error case
							}
							$('#addCouponModal').modal('hide');
						});
			};
			
			it.wallet.trashCoupon = function(e) {
				var target = e.target;
				var targetId = target.id;
				var couponIdExtractRegex = /^couponTrash_(.*)/;
				var couponId = couponIdExtractRegex.exec(targetId)[1];
				var couponIds = [];
				couponIds.push(couponId);
				$.post('deleteCoupons.do', {'couponIds': JSON.stringify(couponIds)}, function(data) {
					var ret = $.parseJSON(data);
					if (ret.success === true) {
						$('#'+targetId).tooltip('hide');
						$('#coupon_'+couponId).remove();
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
				$('.coupon-trash').click(it.wallet.trashCoupon).tooltip();
				$('.daysToExpireDetails').tooltip({
					placement: 'right'
				}).click(function(e){
					$(this).parent().find('.hiddenPartOfTime').show();
					$(this).tooltip('hide').remove();
				});
			};
			
			$(function() {
				$('#addCouponToWallet').click(it.wallet.addCoupon);
				var couponsJson = '${myCouponsJson}',
				coupons = JSON.parse(couponsJson);
				it.wallet.appendCoupons(coupons, true);
				it.wallet.addHandlers();
				$('#expiryDate').datepicker();
				$('#addCouponModalBtn').click(it.wallet.clearCouponFormVals);
				$('.checkBoxSelected').live('click', function() {
					$(this).removeClass('checkBoxSelected').addClass('checkBoxUnSelected');
				});
				$('.checkBoxUnSelected').live('click', function(){
					$(this).removeClass('checkBoxUnSelected').addClass('checkBoxSelected');
				});
				
			});
			
		</script>
		<style type="text/css">
			.coupon {
				/*
				background-color: #006DCC;
				*/
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
			
		</style>
	</head>
	<body>
	
		<%@include file="navHeader.jsp" %>
		
		<div class="container" >
			<div class="row-fluid">
				<div class="span2" >
					<%@include file="walletTabs.jsp" %>
				</div>
				<div class="span7" id="couponContainer" >
				 <ul class="thumbnails">
				</ul>
				&nbsp;
				</div>
				<div class="span3" >
					<div class="modal hide fade" id="addCouponModal" style="display:none" >
						<div class="modal-header" >
							<a class="close" data-dismiss="modal">×</a>
							<h2>Add a coupon to wallet </h2>
						</div>
						<div class="modal-body" >
							<form >
								<label>Code: </label>
								<input id="code" type="text" class="span3 couponDetail" placeholder="Coupon Code?" />
								<label>Discount Description: </label>
								<textarea class="span3 couponDetail" id="discountDetails" placeholder="Discount Details"></textarea>
								<label> Expiry Date: </label>
								<input id="expiryDate"  class="couponDetail" type="date" placeholder="Expiry Date(mm/dd/yyyy)"/>
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
							<a id="addCouponToWallet" class="btn btn-primary">Add Coupon</a>
						</div>
					</div>
					<a id="addCouponModalBtn" class="btn btn-primary btn-large" data-toggle="modal" href="#addCouponModal" >Add Coupon to Wallet</a>
						
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
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
			
			it.wallet.appendCoupons = function(coupons) {
				for (var i=0; i<coupons.length;i++) {
					this.appendCoupon(coupons[i]);
				}
			};
			
			it.wallet.appendCoupon = function (coupon) {
	    		var couponHtml = '<li class="span3" id="coupon_';
	    		couponHtml += coupon.id;
	    		couponHtml += '" ><div class="thumbnail coupon" > <span class="label label-success">Yo! New Coupon Added</span><span class="icon-trash icon-white pull-right coupon-trash" id="couponTrash_';
	    		couponHtml += coupon.id;
	    		couponHtml += '" ></span><h3>Details:';
	    		couponHtml += coupon.detail;
	    		couponHtml += '</h3><h5>Code:';
	    		couponHtml += coupon.code;
	    		couponHtml += '</h5><h5>Discount:';
	    		couponHtml += coupon.discount;
	    		couponHtml +=  '</h5></div></li>';
	    		$(couponHtml).appendTo('.thumbnails');
			};
			
			it.wallet.addCoupon = function () {
				var code = $('#code').val();
				var expiryDateInMillis = $('#expiryDate').datepicker('getDate').getTime();
				var detail = $('#discountDetails').val();
				var coupon = {
					code: code,
					detail: detail,
					expiryDateInMillis: expiryDateInMillis
				};
				var coupons = [];
				coupons.push(coupon);
				$.post('saveCoupons.do',
						{'coupons': JSON.stringify(coupons)},
						function(data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								it.wallet.appendCoupons(ret.result);
								it.wallet.addHandlers();
								setTimeout("$('div.thumbnail span.label').hide().remove();", 5000);
							} else {
								//Handle error case
							}
							$('#addCouponModal').modal('hide');
						}, function(data) {
							//Handle error case
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
				$('.coupon-trash').click(it.wallet.trashCoupon);
			};
			
			$(function() {
				$('#addCouponToWallet').click(it.wallet.addCoupon);
				it.wallet.addHandlers();
				$('#expiryDate').datepicker();
			});
			
		</script>
		<style type="text/css">
			.coupon {
				background: none repeat scroll 0 0 #CCCCFF;
			};
			
		</style>
	</head>
	<body>
	
		<%@include file="navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<div class="span9" id="couponContainer" >
				 <% 
				 	List<Coupon> myCoupons = (List<Coupon>) request.getAttribute("myCoupons");
				 %>	
				 <ul class="thumbnails">
				 <% 
				    for (Coupon coupon : myCoupons) {
				    	%>
				    		<li class="span3" id="coupon_<%=coupon.getId()%>">
								<div class="thumbnail coupon"  >
								<span class="icon-trash icon-white pull-right coupon-trash" id="couponTrash_<%=coupon.getId()%>"></span>
								<h3>Details:  <%=coupon.getDetail()%> </h3>
								<h5>Code: <%=coupon.getCode()%> </h5>
								<h5>Discount: <%=coupon.getDiscount()%> </h5>
								</div>
							</li>
				    	<%
					}
				%>
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
							<form class="well">
								<label>Code: </label>
								<input id="code" type="text" class="span3" placeholder="Coupon Code?" />
								<label>Discount Description: </label>
								<textarea class="span3" id="discountDetails" placeholder="Discount Details"></textarea>
								<label> Expiry Date: </label>
								<input id="expiryDate" type="date" placeholder="Expiry Date(mm/dd/yyyy)"/>
							</form>
						</div>
						<div class="modal-footer" >
							<a href="#" class="btn" data-dismiss="modal">Cancel</a>
							<a id="addCouponToWallet" class="btn btn-primary">Add Coupon</a>
						</div>
					</div>
					<a class="btn btn-primary btn-large" data-toggle="modal" href="#addCouponModal" >Add Coupon to Wallet</a>
						
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp" %>
	
	</body>
</html>
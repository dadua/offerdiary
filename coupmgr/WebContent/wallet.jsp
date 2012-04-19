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
		<%@include file="jqueryAllInclude.html" %>
		<%@include file="fbLoginAboveHeadJs.jsp" %>
		<%@include file="bootstrapHeadInclude.html" %>
		<script type="text/javascript">
			var it = it || {};
			it.wallet = it.wallet || {};
			
			it.wallet.appendCoupons = function(coupons) {
				for (var i=0; i<coupons.length;i++) {
					this.appendCoupon(coupons[i]);
				}
			};
			
			it.wallet.appendCoupon = function (coupon) {
	    		var couponHtml = '<li class="span3"><div class="thumbnail"> <span class="label label-success pull-right">Yo! New Coupon Added</span><h3>Details:';
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
				var detail = $('#discountDetails').val();
				var coupon = {
					code: code,
					detail: detail
				};
				var coupons = [];
				coupons.push(coupon);
				$.post('saveCoupons.do',
						{'coupons': JSON.stringify(coupons)},
						function(data) {
							var ret = $.parseJSON(data);
							if (ret.success === true) {
								it.wallet.appendCoupons(ret.result);
								$('#addCouponModal').modal('hide');
								setTimeout("$('div.thumbnail span.label').hide();", 5000)
							} else {
								//Add failed
							}
						});
			};
			
			$(function() {
				$('#addCouponToWallet').click(it.wallet.addCoupon);
			});
		</script>
	</head>
	<body>
			<div class="it-nav navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<ul class="nav">
						<li class="active">
							<a href="#">Home</a>
						</li>
						<li><a href="#">About Us</a></li>
						<li><a href="#">Blog</a></li>
						<li><a href="#">Terms</a></li>
						<li><a href="#">Privacy</a></li>
					</ul>
				</div>
			</div>
		</div>
		
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
				    		<li class="span3">
								<div class="thumbnail">
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
								<input id="code" type="text" class="span3" placeholder="Coupon Code?">
								<label>Discount Description: </label>
								<input id="discountDetails" type="text" class="span3" placeholder="Discount Details">
								<label>Store Name</label>
								<input id="storeName" type="text" class="span3" placeholder="Store Name">
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
		
		<%@include file="fbInitAboveBodyEnd.jsp" %>
	
	</body>
</html>
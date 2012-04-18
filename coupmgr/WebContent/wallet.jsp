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
			it.wallet.addCoupon = function () {
				debugger;
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
						function() {
							if(console) {
								console.debug('Coupon Saved');
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
								<h3>Details: </h3> <%=coupon.getDetail()%>
								<h5>Code:</h5>  <%=coupon.getCode()%>
								<h5>Discount:</h5> <%=coupon.getDiscount()%>
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
							<a class="close" data-dismiss="modal">�</a>
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
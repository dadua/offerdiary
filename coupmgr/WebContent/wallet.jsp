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
			it.wallet.showAddForm = function () {
				
			};
			
			$(function() {
				//$('#addCouponToWallet').click(function(){});
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
				<img src="http://placehold.it/260x180" alt="">
				<h5><%=coupon.getDetail()%></h5>
				<p>Thumbnail caption right here...</p>
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
								<input type="text" class="span3" placeholder="Coupon Code?">
								<label>Discount Description: </label>
								<input type="text" class="span3" placeholder="Discount Details">
								<label>Store Name</label>
								<input type="text" class="span3" placeholder="Store Name">
							</form>
						</div>
						<div class="modal-footer" >
							<a href="#" class="btn" data-dismiss="modal">Cancel</a>
							<a id="addCouponFooter" href="#" class="btn btn-primary">Add Coupon</a>
						</div>
					</div>
					<a class="btn btn-primary" data-toggle="modal" href="#addCouponModal" >Add Coupon to Wallet</a>
						
				</div>
			</div>
		</div>
		
		<%@include file="fbInitAboveBodyEnd.jsp" %>
	
	</body>
</html>
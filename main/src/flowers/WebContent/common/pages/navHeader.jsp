<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

	<style>
		.topAlign {
			margin-top: 10px;
		}
		div.tile-div{
			height: 100px;
			padding: 10px;
			text-align:center;
		}
		.brandImage {
		}
		
		.featureTabs {
			margin-left: 30px;
			margin-top: 30px;
		}
		.tinyPic {
			width: 19px;
			height: 19px;
		}
		
		</style>
		
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container navBarItems">
        			<a class="brand" href="home.do">
        				<img src="images/logo_tag.png" class="brandImage" alt="Flower Accounts" />
        			</a>
        			<c:if test="${userSession!=null}">
        			<div class="pull-right">
        			<a href="logout.do" class="btn">Logout</a>
        			</div>
        			</c:if>
	        		<c:if test="${userSession!=null}">
	       				<ul class="featureTabs nav nav-tabs" style="margin-left: 25px; margin-top:5px;color:#0088CC;">
		       				<li class="divider-vertical"></li>
		       				<li class="featureTab" id="flowersTab" >
			       				<a href="flowers.do" style="color:#0088CC;">Flowers</a>
		       				</li>
		       				<li class="divider-vertical"></li>
		       				<li class="featureTab" id="suppliersTab" >
			       				<a href="suppliers.do" style="color:#0088CC;">Suppliers</a>
			       			</li>
			       			<li class="divider-vertical"></li>
			       			<li class="featureTab" id="customersTab" >
				       			<a href="customers.do" style="color:#0088CC;">Customers</a>
			       			</li>
			       			<li class="divider-vertical"></li>
			       			<li class="featureTab" id="transactionsTab" >
				       			<a href="transactions.do" style="color:#0088CC;">Transactions</a>
			       			</li>
			       			<li class="divider-vertical"></li>
						</ul>
					</c:if>
        		</div>
      		</div>
      		<div class="container">
	       		<%@include file="/common/pages/actionInfoRow.jsp" %>
       		</div>
 		</div>
 		<script type="text/javascript" >
 			$(function(){
	 			$('.featureTabs > .active + .divider-vertical').hide().prev().prev().hide()
 			});
 		
 		</script>
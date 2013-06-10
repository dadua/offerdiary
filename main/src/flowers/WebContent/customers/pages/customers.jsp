<%@include file="/common/pages/headBegin.jsp" %>

		<title>Customers</title>
		<script src="customers/js/customers.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			$('.allCustomers').addClass('active');
			$('#customersTab').addClass('active');
			var customersJson = '${entitiesJson}',
				customers = $.parseJSON(customersJson);
			
			it.customer.initCustomersUI(customers);
		});
		</script>
		<style type="text/css">
			<%@include file="/common/css/layout.css" %>
		</style>

<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<div class="row">
				<div class="span2">
					<div class="span2 container-fluid options-left-container">
						<%@include file="/customers/pages/customerOptions.jsp" %>
	                </div>
	            </div>
				
	            <div class="span10 "  >
	                <div class="container-fluid content-container">
	                    <div class="row-fluid">
	                    	<div class="span6">
		                    	<h3 class="bluishText">Customers</h3>
	                    	</div>
	                    	<div class="span5">
		                        <br/>
		                        <div class="form-search">
		                            <div class="input-append">
		                                <input id="searchQuery" class="customerSearch search-query input-xlarge" type="text" placeholder="Search Customers" />
		                                <button class="btn"><i class="icon-search"></i></button>
		                            </div>
		                        </div>
	                        </div>
	                    </div>
	                    <br/>
	
	                    <div class="row-fluid">
	                        <div id="customerContainerFluid" class="container-fluid">
	                        </div>
	                        <div class="container-fluid">
		                        <div id="customerPaginationContainer" class="row-fluid span6 offset6">
		                        </div>
		                    </div>
	                    </div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

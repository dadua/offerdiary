<%@include file="/common/pages/headBegin.jsp" %>

		<title>Customers</title>
		<script src="customers/js/customers.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			$('.allCustomers').addClass('active');
			$('#customersTab').addClass('active');
			var customersJson = '${entitiesJson}',
				customers = $.parseJSON(customersJson);
			
			it.customer.list.initCustomersUI(customers);
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
	                        
	                        <div class="span1">
		                        <br>
		                        <div class="btn-group pull-right">
			                        <a class="btn btn-small btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
			                        Actions
			                        <span class="caret"></span>
			                        </a>
			                        <ul class="actionsPanel dropdown-menu">
			                        	<li> <a class="deleteSelectedAction" href="#">Delete Selected</a></li>
			                        </ul>
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
			<div class="templates">
				<%@include file="/common/pages/deleteConfirmModal.jsp" %>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

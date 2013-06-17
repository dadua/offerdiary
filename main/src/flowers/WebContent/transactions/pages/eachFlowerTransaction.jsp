<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>

		<title>Flower Transactions</title>
		
		<script src="transactions/js/flowerTransactions.js" type="text/javascript"></script>
		<script src="customers/js/customers.js" type="text/javascript"></script>
		<script src="suppliers/js/suppliers.js" type="text/javascript"></script>
		<script src="flowers/js/flower.js" type="text/javascript"></script>
		<script src="common/js/entityplotter.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function () {
			    it.flowertx.plotAddNew ({});
			    
			    /*
			    var customerJson = '${entityJson}',
			    	customer = $.parseJSON(customerJson),
			    	operation = '${operation}';
			    it.customer.plot(operation, customer);
			    $('#transactionsTab').addClass('active');
			    
			    var _onCustomerViewEdit = function () {
					$('.customerDetailOptions li').removeClass('active');
					$('.viewEditCustomer').removeClass('hide').addClass('active');
				};
				
				$('#eachCustomerContainerFluid').on('entitySaved', function (e) {
				    _onCustomerViewEdit();
				});
				if (operation === 'ADDNEW') {
				    $('.addCustomer').addClass('active');
				} else if (operation === 'EDIT' || operation === 'VIEW') {
					_onCustomerViewEdit();
				}
				*/
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
						<%@include file="/transactions/pages/transactionOptions.jsp" %>
	                </div>
	            </div>
				
	            <div class="span10"  >
	                <div class="container-fluid  content-container">
	                
	                
	                	<%@include file="/transactions/pages/customerChoose.jsp" %>
	                	
	                	
	                	<%@include file="/transactions/pages/supplierChoose.jsp" %>
	                    
	                    <div class="row-fluid customerDetail contactDetail hide">
	                		<div id="eachCustomerContainerFluid" class="span8 container-fluid">
	                		</div>
	                		
	                		</div>
	                	</div>
	                    
	                    <div class="row-fluid supplierDetail contactDetail hide">
	                		<div id="eachSupplierContainerFluid" class="span8 container-fluid">
	                		
	                		</div>
	                	</div>
	                	
	                	<%@include file="/transactions/pages/flowerTransactionEntries.jsp" %>
	                	

			             
			             <div class="row-fluid navigationControls hide">
	                	
		                	<div class="navigationControl forwardControl pull-right btn btn-info contactChoose customerChoose">Select a Customer</div>
		                	<div class="navigationControl forwardControl pull-right btn btn-info contactConfirm customerChoose">Add Transaction for Selected Customer</div>
		                	
		                	<div class="navigationControl forwardControl pull-right btn btn-info contactChoose supplierChoose">Select a Supplier</div>
		                	<div class="navigationControl forwardControl pull-right btn btn-info contactConfirm supplierChoose">Add Transaction for Selected Supplier</div>
		                	
		                	<div class="navigationControl forwardControl pull-right btn btn-info saveTransaction">Save Transaction</div>
		                	
		                	<div class="navigationControl navigationHelp"></div>
		                	
	                	</div>
	                    
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

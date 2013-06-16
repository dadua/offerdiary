<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>

		<title>Customers</title>
		
		<script src="customers/js/customers.js" type="text/javascript"></script>
		<script src="common/js/entityplotter.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function () {
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
						<ul class="nav nav-list customerDetailOptions">
	                        <li class="nav-header">Options:</li>
	                        <li class="divider " style="margin-bottom: 4px;margin-top: 0px;"></li>
	                        <li class="allCustomers"><a href="customers.do">All Customers</a></li>
	                        <li class="addCustomer"><a href="viewAddNewCustomer.do">Add New Customer</a></li>
	                        <li class="viewEditCustomer hide"><a href="#">Customer Details</a></li>
	                    </ul>
	                </div>
	            </div>
				
				
	            <div class="span10"  >
	                <div class="container-fluid content-container">
	                	<div class="row-fluid">
	                		<div id="eachCustomerContainerFluid" class="span8 container-fluid">
	                		
	                		</div>
	                	</div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

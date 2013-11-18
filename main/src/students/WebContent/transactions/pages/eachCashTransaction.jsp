<%@include file="/common/pages/headBegin.jsp" %>

		<title>Cash Transaction</title>
		
		<script src="transactions/js/cashTransactions.js" type="text/javascript"></script>
		<script src="customers/js/customers.js" type="text/javascript"></script>
		<script src="suppliers/js/suppliers.js" type="text/javascript"></script>
		<script src="flowers/js/flower.js" type="text/javascript"></script>
		<script src="common/js/entityplotter.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function () {
			    $('#transactionsTab').addClass('active');
			    
			    var cashTxJSON = '${entityJson}',
			    	operation = '${operation}',
			    	txType = '${txType}';
			    
			    if (operation === 'ADDNEW') {
					it.cashtx.plotAddNew ({transactionType: txType});
			    } else if (operation === 'VIEW' || operation === 'EDIT') {
					it.cashtx.plotUI(operation, cashTxJSON);
			    }
			    
			    /*
			    var customerJson = '${entityJson}',
			    	customer = $.parseJSON(customerJson),
			    	operation = '${operation}';
			    it.customer.plot(operation, customer);
			    
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
	                    
	                    <div class="row-fluid supplierDetail contactDetail hide">
	                		<div id="eachSupplierContainerFluid" class="span8 container-fluid">
	                		
	                		</div>
	                	</div>
		                <div class="row-fluid hide cashTxEntryRowFluid">
							<div id="cashTransactionContainer" class="container-fluid">
							</div>
						</div>
	             
	             <div class="hide flowerTxEntryTemplate">
	             	<table>
		             	<tr class="eachTransactionEntryRowTemplate hide">
			                <td class="valueViewElement"><a href="#" class="flower"></a></td>
			                <td class="valueEntryElement hide">
				                <div class="control-group" style="margin-bottom: 0px;">
					                <div class="controls">
						                <select name="flower" class="valueEntrySelectBox input-medium">
						                	<%--
						                	<option value="flower_id">
						                		flower.name
						                	</option>
						                	 --%>
						                </select>
						                <span class="help-block hide" style="font-size:.7em;">* </span>
					                </div>
				                </div>
			                </td>
			                
			                <td class="valueViewElement uniqueId"></td>
			                
			                <td class="valueViewElement date"></td>
			                <td class="valueEntryElement hide">
				                <div class="control-group" style="margin-bottom: 0px;">
					                <div class="controls">
						                <input name="date" type="text" class="valueEntryInputTextBox input-medium"></input>
						                <span class="help-block hide" style="font-size:.7em;">* </span>
					                </div>
				                </div>
			                </td>
			                
			                <td class="valueViewElement unitPrice"></td>
			                <td class="valueEntryElement hide">
				                <div class="control-group" style="margin-bottom: 0px;">
					                <div class="controls">
						                <input name="unitPrice" type="text" class="valueEntryInputTextBox input-small"></input>
						                <span class="help-block hide" style="font-size:.7em;">* </span>
					                </div>
				                </div>
			                </td>
			                
			                <td class="valueViewElement quantity"></td>
			                <td class="valueEntryElement hide">
				                <div class="control-group" style="margin-bottom: 0px;">
					                <div class="controls">
						                <input name="quantity" type="text" class="valueEntryInputTextBox input-small"></input>
						                <span class="help-block hide" style="font-size:.7em;">* </span>
					                </div>
				                </div>
			                </td>
			                
			                <td class="valueViewElement totalCost"></td>
			                <td class="valueEntryElement hide">
				                <div class="control-group" style="margin-bottom: 0px;">
					                <div class="controls">
						                <input name="totalCost" type="text" class="uneditable-input valueEntryInputTextBox input-small"></input>
						                <span class="help-block hide" style="font-size:.7em;">* </span>
					                </div>
				                </div>
			                </td>
			                
			                <td class="valueEntryElement hide">
			                	<button class="btn btn-mini addNewTxEntry">+</button>
			                	<button class="btn btn-mini removeTxEntry">-</button>
			                </td>
	                	</tr>
	                </table>
	             </div>
			             
	                
	                
	                	
                                <%--
                                <div id="saveTransaction" class="navigationControl hide forwardControl pull-right btn btn-info txOperation">Save Transaction</div>
                                <div id="editTransaction" class="navigationControl hide forwardControl pull-right btn btn-info txOperation">Edit Transaction</div>
                                <div id="cancelTransaction" class="navigationControl hide forwardControl pull-right btn btn-info txOperation">Cancel Transaction</div>
                                <div class="navigationControl forwardControl pull-right btn btn-info contactConfirm supplierChoose">Add Transaction for Selected Supplier</div>
                                <div class="navigationControl forwardControl pull-right btn btn-info contactConfirm customerChoose">Add Transaction for Selected Customer</div>
                                --%>
                      	<div class="container-fluid navigationControls">
		                	<div id="selectCustomer" class="navigationControl hide forwardControl pull-right btn btn-info contactChoose customerChoose">Add Transactions</div>
		                	<div id="selectSupplier" class="navigationControl hide forwardControl pull-right btn btn-info contactChoose supplierChoose">Add Transactions</div>
		                	<div id="saveTransaction" class="navigationControl hide forwardControl pull-right btn btn-info txOperation">Save Transaction</div>
		                	<div id="editTransaction" class="navigationControl hide forwardControl pull-right btn btn-info txOperation">Edit Transaction</div>
		                	<div class="navigationControl navigationHelp"></div>
	                	</div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

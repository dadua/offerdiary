<%@include file="/common/pages/headBegin.jsp" %>

		<title>Suppliers</title>
		
		<script src="common/js/entityplotter.js" type="text/javascript"></script>
		<script src="suppliers/js/suppliers.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function () {
			    var supplierJson = '${entityJson}',
			    	supplier = $.parseJSON(supplierJson),
			    	operation = '${operation}';
			    it.supplier.plot(operation, supplier);
			    $('#suppliersTab').addClass('active');
			    
			    var _onSupplierViewEdit = function () {
					$('.supplierDetailOptions li').removeClass('active');
					$('.viewEditSupplier').removeClass('hide').addClass('active');
				};
				
				$('#eachSupplierContainerFluid').on('entitySaved', function (e) {
				    _onSupplierViewEdit();
				});
				
				if (operation === 'ADDNEW') {
				    $('.addSupplier').addClass('active');
				} else if (operation === 'EDIT' || operation === 'VIEW') {
				    _onSupplierViewEdit();
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
						<ul class="nav nav-list supplierDetailOptions">
	                        <li class="nav-header">Options:</li>
	                        <li class="divider " style="margin-bottom: 4px;margin-top: 0px;"></li>
	                        <li class="allSuppliers"><a href="suppliers.do">All Suppliers</a></li>
	                        <li class="addSupplier"><a href="viewAddNewSupplier.do">Add New Supplier</a></li>
	                        <li class="viewEditSupplier hide"><a href="#">Supplier Details</a></li>
	                    </ul>
	                </div>
	            </div>
				
	            <div class="span10"  >
	                <div class="container-fluid content-container">
	                	<div class="row-fluid">
	                		<div id="eachSupplierContainerFluid" class="span8 container-fluid">
	                		
	                		</div>
	                	</div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

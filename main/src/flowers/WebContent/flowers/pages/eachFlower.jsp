<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>

		<title>Flowers</title>
		
		<script src="flowers/js/flower.js" type="text/javascript"></script>
		<script src="customers/js/customers.js" type="text/javascript"></script>
		<script src="suppliers/js/suppliers.js" type="text/javascript"></script>
		<script src="common/js/entityplotter.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function () {
			    var flowerJson = '${entityJson}',
			    	flower = $.parseJSON(flowerJson),
			    	operation = '${operation}';
			    it.flower.plot(operation, flower);
			    $('#flowersTab').addClass('active');
			    
			    var _onFlowerViewEdit = function (_flowerp) {
					var _flower = {};
					if (typeof _flowerp === 'object' && typeof _flowerp.id == 'number') {
					    _flower = _flowerp;
					} else {
					    _flower = flower;
					}
					$('.flowerDetailOptions li').removeClass('active');
					$('.viewEditFlower').removeClass('hide').addClass('active');
					it.flower.fetchCustomers(_flower);
					it.flower.fetchSuppliers(_flower);
			    };
			    
			    $('#eachFlowerContainerFluid').on('entitySaved', function (e, id) {
					_onFlowerViewEdit({id: id});
			    });
			    
			    if (operation === 'ADDNEW') {
					$('.addFlower').addClass('active');
				
			    } else if (operation === 'EDIT' || operation === 'VIEW') {
					_onFlowerViewEdit();
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
						<%@include file="/flowers/pages/flowerDetailOptions.jsp" %>
	                </div>
	            </div>
				
	            <div class="span10"  >
	                <div class="container-fluid content-container">
	                	<div class="row-fluid">
	                		<div id="eachFlowerContainerFluid" class="span6 container-fluid">
	                		
	                		</div>
	                	</div>
	                	<br/>
	                	<div class="row-fluid viewEditFlower hide">
	                		<div  class="span6 container-fluid">
	                			<div class="row-fluid customersWithThisFlower">
	                			Customers dealing in this flower
	                			</div>
	                			<br/>
	                			<div class="row-fluid">
	                				<div id="customersContainerFluid" class="container-fluid">
	                				</div>
	                			</div>
	                		</div>
	                		
	                		<div  class="span6 container-fluid">
	                			<div class="row-fluid suppliersWithThisFlower">
	                			Suppliers dealing in this flower
	                			</div>
	                			<br/>
	                			<div class="row-fluid">
	                				<div id="suppliersContainerFluid" class="container-fluid">
	                				</div>
	                			</div>
	                		</div>
	                	</div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

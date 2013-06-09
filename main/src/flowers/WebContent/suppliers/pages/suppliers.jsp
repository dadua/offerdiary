<%@include file="/common/pages/headBegin.jsp" %>

		<title>Suppliers</title>
		<script src="suppliers/js/suppliers.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			$('.allSuppliers').addClass('active');
			$('#suppliersTab').addClass('active');
			var suppliersJson = '${entitiesJson}',
				suppliers = $.parseJSON(suppliersJson);
			it.supplier.initSuppliersUI(suppliers);
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
						<%@include file="/suppliers/pages/supplierOptions.jsp" %>
	                </div>
	            </div>
				
	            <div class="span10 "  >
	                <div class="container-fluid content-container">
	                    <div class="row-fluid">
	                    	<div class="span6">
		                    	<h3 class="bluishText">Suppliers</h3>
	                    	</div>
	                    	<div class="span5">
		                        <br/>
		                        <div class="form-search">
		                            <div class="input-append">
		                                <input id="searchQuery" class="supplierSearch search-query input-xlarge" type="text" placeholder="Search Flowers" />
		                                <button class="btn"><i class="icon-search"></i></button>
		                            </div>
		                        </div>
	                        </div>
	                    </div>
	                    <br/>
	                    <br/>
	
	                    <div class="row-fluid">
	                        <div id="supplierContainerFluid" class="container-fluid">
	                        </div>
	                        <div class="container-fluid">
		                        <div id="supplierPaginationContainer" class="row-fluid span6 offset6">
		                        </div>
		                    </div>
	                    </div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

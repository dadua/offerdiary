<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>

		<title>Flowers</title>
		
		<script src="flowers/js/flower.js" type="text/javascript"></script>
		<script src="common/js/entityplotter.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function () {
			    var flowerJson = '${entityJson}',
			    	flower = $.parseJSON(flowerJson),
			    	operation = '${operation}';
			    it.flower.plot(operation, flower);
			    $('#flowersTab').addClass('active');
			    
			    var _onFlowerViewEdit = function () {
					$('.flowerDetailOptions li').removeClass('active');
					$('.viewEditFlower').removeClass('hide').addClass('active');
			    };
			    
			    $('#eachFlowerContainerFluid').on('entitySaved', function (e) {
					_onFlowerViewEdit();
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
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

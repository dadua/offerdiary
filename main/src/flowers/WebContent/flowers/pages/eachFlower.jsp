<%@page import="com.itech.web.LoginConstants"%>
<%@include file="/common/pages/headBegin.jsp" %>

		<title>Flowers</title>
		<script type="text/javascript">
		</script>
		<style type="text/css">
			<%@include file="/common/css/layout.css" %>
		</style>

<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<div class="row">
				<div class="span2">
					<div class="span2 container-fluid options-left-container">
						<%@include file="/flowers/pages/flowerOptions.jsp" %>
	                </div>
	            </div>
				
	            <div class="span10"  >
	                <div id="eachFlowerContainerFluid" class="container-fluid content-container">
		                <div class="row-fluid">
			                <span class="bluishText largeTitleFontSize">
			                	Flower Details
			                </span>
			                <span class="pull-right">
			                    <button class="operation editBtn btn btn-mini btn-info">
			                        Edit
			                    </button>
			                    <button class="operation addNewBtn btn btn-mini btn-success hide">
			                        Add New
			                    </button>
			                    <button class="operation saveBtn  btn btn-mini btn-success hide">
			                        Save
			                    </button>
			                    <button class="operation cancelBtn btn btn-mini btn-success hide">
			                        Cancel
			                    </button>
			                </span>
			            </div>
			            <br/>
			            
			            <div class="row-fluid">
			                <table class="table table-striped table-bordered">
			                    <tbody>
			                        <tr>
			                            <td><strong>Name</strong></td>
			                            <td class="flowerName valueViewElement"></td>
			                            <td class="valueEntryElement hide">
			                                <div class="control-group" style="margin-bottom: 0px;">
			                                    <div class="controls">
			                                        <input type="text" class="flowerNameText"></input>
			                                        <span class="help-block hide" style="font-size:.7em;">* Flower Name can't be empty</span>
			                                    </div>
			                                </div>
			                            </td>
			                        </tr>
			                        <tr>
			                            <td><strong>Color</strong></td>
			                            <td class="flowerColor valueViewElement"></td>
			                            <td class="valueEntryElement hide">
			                                <input type="text" class="flowerColorText"></input>
			                            </td>
			                        </tr>
			                    </tbody>
			                </table>
			            </div>
	                </div>
	            </div>
			</div>
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>

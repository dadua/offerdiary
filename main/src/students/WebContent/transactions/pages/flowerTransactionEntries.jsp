				<div class="row-fluid hide flowerTxEntryRowFluid">
					<div class="container-fluid">
						<h4 class="bluishText">Transaction Details</h4>
					
	                	<table id="transactionEntries" class="table table-striped table-condensed table-bordered txTable">
		                	<thead>
			                	<tr class="headingRow">
				                	<th>Flower</th>
				                	<th class="valueViewElement">UniqueId</th>
				                	<th>Date</th>
				                	<th>Price</th>
				                	<th>Quantity</th>
				                	<th>Total Cost</th>
				                	<th class="valueEntryElement"></th>
			                	</tr>
			                </thead>
			                <tbody>
			    
			                </tbody>
			             </table>
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
			             

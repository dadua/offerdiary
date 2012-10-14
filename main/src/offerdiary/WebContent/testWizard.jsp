<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    

<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Sample blank page</title>
		
		<%@include file="common/header.jsp" %>
		<script src="js/it/wizard.js" > </script>
		
		<script type="text/javascript">
			$(function(){
			    //TODO: wizardId, and title should be passed in newInstance
			    // also add data.. in newInstance
			
			    //TODO: selector, and title should be passed in newInstance
			    var step = it.wizard.step.newInstance();
				step.setTitle('This is a sample step');
			    step.setHtmlTemplateSelector('#randomTemplate');
			    step.setPlotHtmlFromDataCb(function(data){
					if (data&& data.test) {
					    $('#testForm1').val(data.test);
					}
			    });
				
			    var steps = [];
			    steps.push(step);
			    var step2 = it.wizard.step.newInstance('Another step title','#another');
			    steps.push(step2);
			    var step3 = it.wizard.step.newInstance();
			    step3.setTitle('step title 3');
			    step3.setHtmlTemplateSelector('#step3');
			    steps.push(step3);
			    var sampleWizard = it.wizard.newInstance('wizardRoot', steps, {});
			    sampleWizard.setTitle('Sample Wizard Title')
			    /*
			    sampleWizard.setWizardSteps(steps);
			    sampleWizard.setRootId('wizardRoot');
			    */
			    //step.getHtmlTemplateSelector();
			    
			    var validateStep1 = function(){
				    var isValidated = false;
				    if (step.$('#testForm1').val()== '') {
						isValidated = false;
				    } else {
						isValidated = true;
				    }
				    step.publishOnValidationChangeCb(isValidated);
				    return isValidated;
				};
				step.setStepValidator(validateStep1);
				
			    step.$('#testForm1').keyup(validateStep1);
			    var validateStep3 = function(){
				    var isValidated = false;
				    if (step3.$('#change').val()== '') {
						isValidated = false;
				    } else {
						isValidated = true;
				    }
				    step3.publishOnValidationChangeCb(isValidated);
				    return isValidated;
				};
			    step3.$('#change').keyup(validateStep3);
			    step3.setStepValidator(validateStep3);
			    //Bind the sampleWizard's .show method to a click handler
			    $('#testBtn').click(sampleWizard.show);
			});
			
		</script>
		
		<style type="text/css">
		</style>
		
	</head>
	<body>
	
		<%@include file="common/navHeader.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<p> A new row-fluid this is </p>
			</div>
			<div class="row-fluid">
				<div class="span6 thumbnail" >
					<h4>
						c: choose functionality
					</h4>
					
					<button class="btn btn-primary" id="testBtn">Show Wizard</button>
					
					<c:choose>
						<c:when test="true">
							This should print 
						</c:when>
						<c:otherwise>
							This shouldn't print
						</c:otherwise>
					</c:choose>
				</div>
				<div class="span6 box-shadow" >
					<h4>
						c: if functionality
					</h4>
					<c:if test="false">
						Mangu says me no printing.. :) 
					</c:if>
					<c:if test="true">
						prints
					</c:if>
				</div>
			</div>
		</div>
		
		<div id="wizardRoot">
		</div>
		
		<div id="randomTemplate">
			This is some text to be used in a step 1
			<input id="testForm1" type="text" />
		</div>
		<div id="another">This is some more text for step2
			<input id="testChange" type="text" />
		</div>
		<div id="step3">This is some more text for step3
			<input id="change" type="text" />
		</div>
		
		<%@include file="common/footer.jsp" %>
	</body>
</html>
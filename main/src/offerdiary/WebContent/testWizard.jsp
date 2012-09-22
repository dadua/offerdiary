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
			    var sampleWizard = it.wizard.newInstance();
			    var step = it.wizard.step.newInstance();
			    step.setTitle('This is a sample step');
			    step.setHtmlTemplateSelector('#randomTemplate');
			    var steps = []
			    steps.push(step)
			    var step2 = it.wizard.step.newInstance();
			    step2.setTitle('Another step title');
			    step2.setHtmlTemplateSelector('#another');
			    steps.push(step2);
			    sampleWizard.init('wizardRoot', steps, {});
			    sampleWizard.setTitle('Sample Wizard Title')
			    /*
			    sampleWizard.setWizardSteps(steps);
			    sampleWizard.setRootId('wizardRoot');
			    */
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
					
					<button class="btn btn-primary" id="testBtn">On Click Show Modal</button>
					
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
		
		<div id="randomTemplate">This is some text to be used in a step</div>
		<div id="another">This is some more text</div>
		
		<%@include file="common/footer.jsp" %>
	</body>
</html>
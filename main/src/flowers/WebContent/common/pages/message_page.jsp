<%@include file="/common/pages/headBegin.jsp" %>
 
    <style type="text/css">
    
            <%@include file="/common/css/layout.css" %>
    </style>

    <title>Offer Diary</title>
    
<%@include file="/common/pages/bodyBegin.jsp" %>	
	<div class="container" >
   		<div class="row">
   			<div class="span6 offset3">
		   		<strong>
					${errorMessage}
				</strong>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="span6 offset3">
				<a class="btn btn-primary" href="diary.do" > Go to My Diary</a>
			</div>
		</div>
	</div>

<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
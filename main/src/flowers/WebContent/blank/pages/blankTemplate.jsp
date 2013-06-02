<%@include file="/common/pages/headBegin.jsp" %>
		<title>Sample blank page</title>
		
		<script type="text/javascript">
			var it = it || {};
			
		</script>
		
		<style type="text/css">
		</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<div class="row-fluid">
				<p> A new row-fluid this is </p>
			</div>
			<div class="row-fluid">
				<div class="span6 thumbnail" >
					<h4>
						c: choose functionality
					</h4>
					
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
						no printing.. :) 
					</c:if>
					<c:if test="true">
						prints
					</c:if>
				</div>
			</div>
		</div>
		
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
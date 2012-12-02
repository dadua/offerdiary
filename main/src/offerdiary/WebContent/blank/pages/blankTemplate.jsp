<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    

<!DOCTYPE html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Sample blank page</title>
		
		<%@include file="common/header.jsp" %>
		
		<script type="text/javascript">
			var it = it || {};
			
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
		
		
		<%@include file="common/footer.jsp" %>
	</body>
</html>
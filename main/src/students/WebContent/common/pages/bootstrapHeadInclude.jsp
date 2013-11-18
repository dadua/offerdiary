<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--
<link href="bootstrap/2_1_1/css/bootstrap.cerulean.css" rel="stylesheet">
-->

<c:choose>
	<c:when test="${odServerMode == 'DEV'}">
	
		<link href="common/libs/bootstrap/2_2_1/css/bootstrap.css" rel="stylesheet">
		<link href="common/libs/bootstrap/2_2_1/css/bootstrap-responsive.css" rel="stylesheet">
		<script type="text/javascript" src="common/libs/bootstrap/2_2_1/js/bootstrap.js" > </script>
		
	</c:when>
	<c:otherwise>
	
		<link href="common/libs/bootstrap/2_2_1/css/bootstrap.min.css" rel="stylesheet">
		<link href="common/libs/bootstrap/2_2_1/css/bootstrap-responsive.css" rel="stylesheet">
		<script type="text/javascript" src="common/libs/bootstrap/2_2_1/js/bootstrap.min.js" > </script>
	
	</c:otherwise>
</c:choose>


<!-- When using navbar navbar-fixed-top add padding top-->
<style type="text/css" >

	.center {
		text-align: center;
	}
</style>

<script type="text/javascript" charset="utf-8">
    <%@include file="/common/js/bs.extend.js" %>
	
</script>


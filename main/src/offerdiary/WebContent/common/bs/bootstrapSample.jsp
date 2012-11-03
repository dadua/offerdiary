<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<!-- 
<%@include file="bootstrapHeadInclude.html" %>
-->
<link href="../../bootstrap/2_1_1/css/bootstrap.css" rel="stylesheet">
<link href="../../bootstrap/2_1_1/css/bootstrap-responsive.css"

	rel="stylesheet">
	
<style type="text/css">

.container {
	color: gray;
}

body {
	padding-top: 40px;
}

.it-nav {
	color: green;
}

.it-debug-border {
	border: 1px solid;
}

.it-debug-size-less { /*height: 60%;*/
	
}
</style>

</head>
<body>
	<div class="it-nav navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<ul class="nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">Link</a>
					</li>
					<li><a href="#">Link</a>
					</li>
				</ul>
			</div>
		</div>
	</div>


	<div class="container">
		<div class="row">
			<div class="span4">
				<p class="it-debug-border">Some content</p>
			</div>
			<div class="span6">
				<p class="it-debug-border">Some more content</p>
			</div>
			<div class="span2 it-debug-size-less">
				<div class="it-debug-border it-debug-size-less">Some more
					content Some more content Some more content Some more content Some
					more content Some more content Some more content
					http://localhost:8080/offerdiary/images/stores/100BestBuy.jpg</div>
			</div>
		</div>

		<div class="row">
			<div class="span8">
				<p class="it-debug-border">Some content</p>
			</div>
		</div>
	</div>

</body>
</html>
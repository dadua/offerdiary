<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Offer Template</title>
<%@include file="common/jqueryAllInclude.html" %>
<%@include file="common/bs/bootstrapHeadInclude.html" %>
</head>
<style type="text/css">
div.offerBlock{
	width: 325px;
	height: 125px; 
	position:relative;
}
.space15{
	width: 140px;
	float: left;
}
span.offerNum{
	font-family:  sans-serif;
	font-size: 20px;
	font-style: normal;
	font-weight: normal;
	text-transform: normal;
	line-height: 1.2em;
}
span.sans-serif-normal{
	font-family:  sans-serif;
	font-size: 14px;
}
span.sans-serif-small{
	font-family:  sans-serif;
	font-size: 12px;
}
span.sans-serif-extra-small{
	font-family:  sans-serif;
	font-size: 10px;
}
.shadow {
  -moz-box-shadow:    3px 3px 5px 6px #ccc;
  -webkit-box-shadow: 3px 3px 5px 6px #ccc;
  box-shadow:         3px 3px 5px 6px #ccc;
}
span.margin-left-10{
	margin-left: 10px;
}
div.offer-icon{
	margin-left:-5px;
	
}
div.margin-zero{
	margin-left: 0px;
}
</style>
<body>
	<div class="row">
		 <div class="span6 offset2">
			<div class="span8">
			
				<!-- Offer Template Code Begins Here | Use it under a div, implemeting a span class of span4 or more -->
				
				<div id="offer_ui_template" class="thumbnail offerBlock shadow">
					<div class="row">
						<div class="span2 pull-left">
									<img alt="store_title" src="images/stores/99labels.jpg">
									<span class="margin-left-10 sans-serif-extra-small">Code: 5455X34</span>
						</div>
						<div class="space15">
							<div class="row margin-zero">
								<span class="offerNum">
									Buy 2 & get 5 Free
								</span>
							</div>
							<div class="row margin-zero">
								<span class="sans-serif-normal">
									99Lables
								</span>
							</div>
							<div class="row margin-zero">
								<span class="sans-serif-small">
									<a href="http://www.99labels.com"
										target="_blank">
										www.99labels.com
									</a>
								</span>
							</div >
							<div class="row margin-zero">
								<span class="sans-serif-small">
									Expire
								</span>
								<i class="icon-calendar"></i>
								<span class="sans-serif-extra-small">
									: 28-Sept, 2012
								</span>
							</div>
						</div>
					</div>
					<div class="row offer-icon" >
						<div class="span2" style="margin-left:15px;">
							<a href="#" onClick="" >
								<i class="icon-trash"></i>
							</a>
							<a href="#" onClick="" >
								<i class="icon-wrench"></i>
							</a>
							<a href="#" onClick="" >
								<i class="icon-envelope"></i>
							</a>
						</div>
					</div>
				</div>
				<!-- End of Offer Template UI -->
				
			</div>
		</div>
	</div>
</body>
</html>
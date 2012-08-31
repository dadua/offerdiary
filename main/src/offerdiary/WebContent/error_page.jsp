<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isErrorPage = "true"%>
<%@ page import = "java.io.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page </title>
<link rel="stylesheet" type="text/css" href="http://dl.dropbox.com/u/8774260/socialbaba/css/pepper-grinder/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" href="css/social.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/jquery-ui.min.js"></script>
<script type="text/javascript">
 $(function(){
	 $('#homePageLink').button();
 });
 </script>
</head>
<body>
	<div id="page" style="height:1700px">
		<div id="logoHeader"><a href="index.html">
		<input id="logoHeaderImage" type="image" src="http://dl.dropbox.com/u/8774260/socialbaba/images/logo.jpg" />
		</a>
		<span id="feedback">
		<iframe src="http://www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2Fapps%2Fapplication.php%3Fid%3D175776209113921&amp;width=292&amp;colorscheme=light&amp;show_faces=false&amp;stream=false&amp;header=false&amp;height=82" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:262px; height:82px;" allowTransparency="true"></iframe>
		<a href="https://www.facebook.com/apps/application.php?id=175776209113921&sk=app_6261817190" target="_blank"><img src="http://dl.dropbox.com/u/8774260/socialbaba/images/feedback.gif" /></a>
		</span>
		</div>
			<div class="cancelActionPanel">
			Error Occurred while processing your request
			 <a href="index.html" id="homePageLink">Go to Home Page</a>
			<br /> <br />
			
		</div>
	</div>
</body>
</html>

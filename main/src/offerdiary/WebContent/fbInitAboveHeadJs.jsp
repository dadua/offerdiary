<%--
This page has dependency on fbInitAboveBodyEnd.jsp for fb-root div
--%>
<script src="https://connect.facebook.net/en_US/all.js"></script>

<script type="text/javascript">
	$(function(){
		FB.init({
			appId : '187105271327076', 
			status : true,
			cookie : true, 
			xfbml : true
		});
	});
</script>

<%--
This page has dependency on fbInitAboveBodyEnd.jsp for fb-root div
--%>
<script src="https://connect.facebook.net/en_US/all.js"></script>

<script type="text/javascript">
	$(function(){
	    try {
			FB.init({
				appId : '187105271327076', 
				status : true,
				cookie : true, 
				xfbml : true
			});
	    } catch (e) {
	    <%-- Ignoring FB load fails,
	    This check would help if we load FB in an async way --%>
		}
	});
</script>

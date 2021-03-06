<%--
This page has dependency on fbInitAboveBodyEnd.jsp for fb-root div
<script src="https://connect.facebook.net/en_US/all.js"></script>
--%>

<div id="fb-root"></div>
<script type="text/javascript">
	
	window.fbAsyncInit = function() {
	    <%-- init the FB JS SDK --%>
	     try {
			FB.init({
				appId : '${fbAppId}', 
				status : true,
				cookie : true, 
				xfbml : true
			});
			$('#loginToFb').removeClass('disabled');
	    } catch (e) {
		    <%-- Ignoring FB load fails,
		    This check would help if we load FB in an async way --%>
		}
	  };
	
	  <%--
	  // Load the SDK's source Asynchronously
	  // Note that the debug version is being actively developed and might 
	  // contain some type checks that are overly strict. 
	  // Please report such bugs using the bugs tool.
	  --%>
	  (function(d, debug){
	     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	     if (d.getElementById(id)) {return;}
	     js = d.createElement('script'); js.id = id; js.async = true;
	     js.src = "//connect.facebook.net/en_US/all" + (debug ? "/debug" : "") + ".js#xfbml=1&appId=${fbAppId}";
	     ref.parentNode.insertBefore(js, ref);
	   }(document, /*debug*/ false));
</script>

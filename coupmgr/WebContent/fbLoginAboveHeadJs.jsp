<%@include file="fbInitAboveHeadJs.jsp" %> 

<%--
	button with id=loginToFb is to present in the DOM
	For e.g.:
	<div >
		<input id="loginToFb" type="button" value="Fetch Authors">
	</div>
 --%>
 
<%--
TODO: Make Permission Flexible
--%>

<script>
	$(function() {
		$(":button").button();
		$('#loginToFb').click(it.fb.checkOrLogin);
	});
	
	var it = it|| {};
	it.fb = it.fb || {};
	
	it.fb = function () {
		
		var onServerUp = null;
		var updatePageOnLogin = function(data){
			if (console) {
				console.debug('Server updated');
			}
		};
		
		var serverUpdate = function(fbData) {
			var fbDJson = JSON.stringify(fbData);
			if (typeof onServerUp === 'function') {
				$.post('setFbAdapter.do', {'fbData': fbDJson}, onServerUp);
			} else {
				$.post('setFbAdapter.do', {'fbData': fbDJson}, updatePageOnLogin);
			}
		};
		
		var handleFbResp = function(response) {
			if (response.authResponse) {
				serverUpdate(response.authResponse);
			} else {
				alert('User LogIn failed!!');
			}
		};
		
		var checkOrLogin = function() {
			FB.getLoginStatus(function(resp) {
				if (resp.status === 'connected') {
					//it.toFetchId.setUser(resp.authResponse.userID);
					serverUpdate(resp.authResponse);
				} else {
					try {
						FB.login(handleFbResp, {
							scope: 'publish_stream, email'});
					} catch (e) {
						$('#errPopupBlocked').show();
					}
				}
			});
		};
		
		return {
			checkOrLogin: checkOrLogin,
			setActionOnServerUp: function(func) {
				if (typeof func === 'function') {
					onServerUp = func;
				} else {
					throw {message: 'a function object has to be inputted'};
				}
			}
		};
	}();
</script>
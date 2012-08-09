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
	});
	
	var it = it|| {};
	it.fb = function () {
		
		var lastOnServerUp = null;
		
		var updatePageOnLogin = function(data){
			if (console) {
				console.debug('Server updated');
			}
		};
		
		var serverUpdate = function(fbData, onServerUp) {
			var fbDJson = JSON.stringify(fbData);
			if (typeof onServerUp === 'function') {
				$.post('setFbAdapter.do', {'fbData': fbDJson}, onServerUp);
			} else {
				$.post('setFbAdapter.do', {'fbData': fbDJson}, updatePageOnLogin);
			}
		};
		
		var handleFbResp = function(response) {
			if (response.authResponse) {
				serverUpdate(response.authResponse, lastOnServerUp);
			} else {
				alert('User LogIn failed!!');
			}
		};
		
		var checkLoginStatusAndUpdateServer = function (onUserNotLoggedInHandler, onServerUp) {
			FB.getLoginStatus(function(resp) {
				if (resp.status === 'connected') {
					serverUpdate(resp.authResponse, onServerUp);
				} else {
					if (typeof onUserNotLoggedInHandler === 'function') {
						onUserNotLoggedInHandler();
					} 
				}
			});
		};
		
		var checkAndLogin = function(e) {
			var onServerUp = e.data.onServerUp;
			if (typeof onServerUp === 'function') {
				lastOnServerUp = onServerUp;
			}
			checkLoginStatusAndUpdateServer(function() {
				try {
					FB.login(handleFbResp, {
						scope: 'publish_stream, email'});
					} catch (e) {
						$('#errPopupBlocked').show();
					}
			}, onServerUp);
		};
		
		return {
			checkLoginStatusAndUpdateServer: checkLoginStatusAndUpdateServer,
			checkAndLogin: checkAndLogin,
			setActionOnServerUp: function(func) {
				if (typeof func === 'function') {
					lastOnServerUp = func;
				} else {
					throw {message: 'a function object has to be inputted'}; 
				}
			}
		};
	}();
</script>
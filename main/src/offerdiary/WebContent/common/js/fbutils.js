var it = it|| {};
it.fb = function () {

    var lastOnServerUp = null;

    var _updatePageOnLogin = function(data){
        if (console) {
            console.debug('Server updated');
        }
    };

    var _serverUpdate = function(fbData, onServerUp) {
        var fbDJson = JSON.stringify(fbData);
        if (typeof onServerUp === 'function') {
            $.post('setFbAdapter.do', {'fbData': fbDJson}, onServerUp);
        } else {
            $.post('setFbAdapter.do', {'fbData': fbDJson}, _updatePageOnLogin);
        }
    };

    var _handleFbResp = function(response) {
        if (response.authResponse) {
            _serverUpdate(response.authResponse, lastOnServerUp);
        } else {
            $('#loginToFb').removeClass('disabled');
            alert('User login failed!!');
        }
    };

    var checkLoginStatusAndUpdateServer = function (onUserNotLoggedInHandler, onServerUp) {
        FB.getLoginStatus(function(resp) {
            if (resp.status === 'connected') {
                _serverUpdate(resp.authResponse, onServerUp);
            } else {
                if (typeof onUserNotLoggedInHandler === 'function') {
                    onUserNotLoggedInHandler();
                } 
            }
        });
    };

    var checkAndLogin = function(e) {
        if ($(this).hasClass('disabled')) {
            return;
        }
        $(this).addClass('disabled');
        var onServerUp = e.data.onServerUp;
        if (typeof onServerUp === 'function') {
            lastOnServerUp = onServerUp;
        }
        checkLoginStatusAndUpdateServer(function() {
            try {
                FB.login(_handleFbResp, {
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


var it = it|| {};
it.fb = function () {

    var _lastOnServerUp = null;

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
            _serverUpdate(response.authResponse, _lastOnServerUp);
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

    var postOnWall = function (options) {
        options = $.extend({
            method: 'feed',
            redirect_uri: 'http://localhost:8080/offerdiary/',
            link: 'http://www.offerdiary.com',
            picture: 'http://ec2-54-245-11-4.us-west-2.compute.amazonaws.com/images/logo_tag.png',
            name: 'Offer Diary',
            caption: 'Never miss an offer',
            description: '',
            callback: function(){
            }
        }, options);

        FB.ui(options, options.callback);
    };

    var checkAndLogin = function(e) {
        if ($(this).hasClass('disabled')) {
            return;
        }
        $(this).addClass('disabled');
        var onServerUp = e.data.onServerUp;
        if (typeof onServerUp === 'function') {
            _lastOnServerUp = onServerUp;
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
                _lastOnServerUp = func;
            } else {
                throw {message: 'a function object has to be inputted'}; 
            }
        },
        postOnWall: postOnWall
    };
}();


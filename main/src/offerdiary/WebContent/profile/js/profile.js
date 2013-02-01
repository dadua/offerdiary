/**
 * 
 */


var it = it || {};
it.profile = it.profile || {};

it.profile.view = function(){
    var _getUserInfo$ = function (userInfo) {
        var _userInfo$ = $('.user_info_template').clone();
        _userInfo$.removeClass('user_info_template').addClass('userInfo');
        return _userInfo$;
    };


    return {
        getUserInfo$: _getUserInfo$
    };
}();


it.profile.refreshUserInfo = function() {
    $.getJSON('getUserInfo.do', function(data){
        var userInfo = data.result;
        it.profile.plotUserInfo(userInfo);
    });
};

it.profile.plotUserInfo = function(userInfo) {

};

it.profile.toggler = function () {
    var _showUserDetails = function () {
        
    },

    _showNotificationsettings = function () {

    },

    _showPoints = function () {

    },

    _showLinkedAccounts = function () {

    };
    
    return {
        showUserDetails: _showUserDetails,
        showNotificationsettings: _showNotificationsettings,
        showPoints: _showPoints,
        showLinkedAccounts: _showLinkedAccounts
    };
}();


/**
 * 
 */


var it = it || {};
it.profile = it.profile || {};

it.profile.view = function(){
    var _getUserInfo$ = function (userInfo) {
        var _userInfo$ = $('.user_info_template').clone();
        userInfo = $.extend({
            name: '',
            age: '-',
            dobMillis: '-',
            city: '-',
            emailId: '-',
            nickName: '-',
            mobileNumber: '-',
            pinCode: '-'
        }, userInfo);
        _userInfo$.removeClass('user_info_template').addClass('userInfo');
        _userInfo$.find('.userName').html(userInfo.name);
        _userInfo$.find('.userCity').html(userInfo.city);
        _userInfo$.find('.userEmail').html(userInfo.emailId);
        _userInfo$.find('.dob').html(userInfo.dobMillis);
        _userInfo$.find('.nickName').html(userInfo.nickName);
        _userInfo$.find('.pinCode').html(userInfo.pinCode);
        _userInfo$.find('.mobileNumber').html(userInfo.mobileNumber);
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
    var userInfo$ = this.view.getUserInfo$(userInfo);
    $('#profileContainer').html(userInfo$);

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


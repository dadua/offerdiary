/**
 * 
 */


var it = it || {};
it.profile = it.profile || {};

it.profile.toggler = function () {
    var _showUserDetails = function () {
        it.profile.userInfo.refresh ();
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


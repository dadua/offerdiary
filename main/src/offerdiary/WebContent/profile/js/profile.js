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
        it.profile.notification.refresh ();
    },

    _showPoints = function () {

    },

    _showChangePassword = function () {
        it.profile.password.plotChangeUI();
    },

    _showLinkedAccounts = function () {

    };
    
    return {
        showUserDetails: _showUserDetails,
        showNotificationsettings: _showNotificationsettings,
        showPoints: _showPoints,
        showLinkedAccounts: _showLinkedAccounts,
        showChangePassword: _showChangePassword
    };
}();


it.profile.addOptionHandlers = function () {
    $('#userDetails').click(this.toggler.showUserDetails);
    $('#notificationSettings').click(this.toggler.showNotificationsettings);
    $('#changePasswordOption').click(this.toggler.showChangePassword);
};


it.profile.init = function () {
    this.toggler.showUserDetails();
    this.addOptionHandlers();
};


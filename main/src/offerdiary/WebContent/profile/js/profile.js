/**
 * 
 */


var it = it || {};
it.profile = it.profile || {};

it.profile.toggler = function () {
    var _showUserDetails = function (e) {
        _resetOptionsLeftContainer();
        $('#userDetails').parent().addClass('active');
        it.profile.userInfo.refresh ();
    },

    _showNotificationsettings = function (e) {
        _resetOptionsLeftContainer();
        $('#notificationSettings').parent().addClass('active');
        it.profile.notification.refresh ();
    },

    _showPoints = function () {

    },

    _showChangePassword = function () {
        _resetOptionsLeftContainer();
        $('#changePasswordOption').parent().addClass('active');
        it.profile.password.plotChangeUI();
    },

    _resetOptionsLeftContainer = function () {
        $('.options-left-container > ul > li').removeClass('active');
    },

    _changePasswordHandler = function (e) {
        e.preventDefault();
        History.pushState({opt:'changePassword'}, 'Change Password', '?o=changePassword');
    },

    _changeDetailsHandler = function (e) {
        e.preventDefault();
        History.pushState({opt: 'details'}, 'Details', '?o=details');
    },

    _changeNotificationSettingsHandler = function (e) {
        e.preventDefault();
        History.pushState({opt: 'notificationSettings'}, 'Notification Settings', '?o=notificationSettings');
    },

    _showLinkedAccounts = function () {

    };

    return {
        showUserDetails: _showUserDetails,
        showNotificationsettings: _showNotificationsettings,
        showPoints: _showPoints,
        showLinkedAccounts: _showLinkedAccounts,
        showChangePassword: _showChangePassword,
        changePasswordHandler: _changePasswordHandler,
        changeDetailsHandler: _changeDetailsHandler,
        changeNotificationSettingsHandler: _changeNotificationSettingsHandler
    };
}();


it.profile.addHistoryHandlers = function () {
    History.Adapter.bind(window,'statechange',function(){
        var state = History.getState(),
            opt = state.data.opt;

        //History.log(state.data, state.title, state.url);
        it.profile.toggleBasedOnOpt(opt);
    });
};


it.profile.addOptionHandlers = function () {
    $('#userDetails').click(this.toggler.changeDetailsHandler);
    $('#notificationSettings').click(this.toggler.changeNotificationSettingsHandler);
    $('#changePasswordOption').click(this.toggler.changePasswordHandler);
};

it.profile.toggleBasedOnOpt = function(opt) {
    if (opt === 'changePassword') {
        this.toggler.showChangePassword();
    } else if (opt === 'notificationSettings') {
        this.toggler.showNotificationsettings();
    } else {
        this.toggler.showUserDetails();
    }
};

it.profile.init = function (opt) {
    this.toggleBasedOnOpt(opt);
    this.addOptionHandlers();
    it.profile.addHistoryHandlers();
};


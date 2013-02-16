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
    	
    	_resetOptionsLeftContainer();
    	$('#changePasswordOption').parent().addClass('active');
        it.profile.password.plotChangeUI();
    },
    
    _resetOptionsLeftContainer = function () {
    	$('.options-left-container > ul > li').removeClass('active');
    },
    
    _changePasswordHandler = function (e) {
    	e.preventDefault();
    	History.pushState(null, null, '?o=changePassword');
    	_showChangePassword();
    },

    _showLinkedAccounts = function () {

    };
    
    return {
        showUserDetails: _showUserDetails,
        showNotificationsettings: _showNotificationsettings,
        showPoints: _showPoints,
        showLinkedAccounts: _showLinkedAccounts,
        showChangePassword: _showChangePassword,
        changePasswordHandler: _changePasswordHandler
    };
}();


it.profile.addHistoryHandlers = function () {
	History.Adapter.bind(window,'statechange',function(){
		var state = History.getState(); 
	    //History.log(State.data, State.title, State.url);
	});
};


it.profile.addOptionHandlers = function () {
    $('#userDetails').click(this.toggler.showUserDetails);
    $('#notificationSettings').click(this.toggler.showNotificationsettings);
    $('#changePasswordOption').click(this.toggler.changePasswordHandler);
};


it.profile.init = function (opt) {
	if (opt === 'changePassword') {
		this.toggler.showChangePassword();
	} else {
		this.toggler.showUserDetails();
	}
    this.addOptionHandlers();
    it.profile.addHistoryHandlers();
};


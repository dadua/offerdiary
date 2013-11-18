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






it.profile.list = it.profile.list || {};

it.profile.list.newInstance = function (containerId$, searchQueryElem$) {
    
    var container$ = $(containerId$),

	    searchQuery$ = $(searchQueryElem$),
	    
	    fetchAll = function() {
	        var q = searchQuery$.val();
	
	        $.post('getProfiles.do', {q:q}, function (respJSON) {
	            var resp = $.parseJSON(respJSON);
	            plotAll(resp.result);
	        });
	    },
	
	    _isOneInited = false,
	    
	    plotAll = function (profiles) {
	        var _$ = view.get$(profiles);
	        container$.html(_$);
	        view.addHandlers();
	        if (!_isOneInited) {
	            addOneHandlers();
	            _isOneInited = true;
	        }
	    }, 
	
	    addOneHandlers = function () {
	        searchQuery$.keyup(fetchAll);
	    },
	
	    view = this.newViewInstance(containerId$);

    return {
        plotAll: plotAll,
        addOneHandlers: addOneHandlers,
        refresh: fetchAll,
        view: view,
        getSelectedItems: view.getSelectedItems
    };

};

it.profile.list.newViewInstance = function (containerId$) {
    var _eachRowHtml = '<tr><td><input type="checkbox" class="rowSelect"></input></td><td ><a class="name"></a></td><td class="color"></td><td class="quantityInStock"></td></tr>',
    
        _tableWithHeadingHtml = '<table class="table entityTable table-striped table-condensed table-bordered"> <thead> <tr><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></input></th><th> Name </th> <th>Color</th><th>Stock in quantity</th></tr> </thead> <tbody></tbody></table>',

        container$ = $(containerId$),
        
        _get$ = function (profiles) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentProfile = {};

            for (var i=0; i< profiles.length; i++) {
                currentProfile = profiles[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentProfile.name).attr('href', 'profile.do?id='+currentProfile.id);
                _tr$.find('.color').html(currentProfile.color);
                _tr$.find('.quantityInStock').html(currentProfile.quantityInStock);
                _tr$.data('entityId', currentProfile.id);
                _tableBody$.append(_tr$);
            }
            return _$;
        },

        _selectAllHandler = function () {
            if ($(this).is(':checked')) {
                container$.find('.entityTable').find('.rowSelect').prop('checked', true);
            } else {
                container$.find('.rowSelect').prop('checked', false);
            }
        },

        _entitySelectedHandler = function (e) {
            var this$ = $(this),
                entityId = this$.closest('tr').data('entityId');

            if (this$.is(':checked')) {
                container$.trigger('entityInListSelected', [entityId, this$]);
            } else {
                container$.trigger('entityInListUnSelected', [entityId, this$]);
            }
        },

        _addHandlers = function () {
            var selectAll$ = container$.find('.entityTable').find('.selectall');
            selectAll$.tooltip().click(_selectAllHandler);

            container$.find('.entityTable').find('.rowSelect').click(_entitySelectedHandler);
        },

        _getSelectedItems = function () {
            var selectedIds = [];
            container$.find('.rowSelect').each(function(i) {
                if ($(this).is(':checked')) {
                    selectedIds.push($(this).closest('tr').data('entityId'));
                }
            });
            return selectedIds;
        };


    return {
        get$: _get$,
        addHandlers: _addHandlers,
        getSelectedItems: _getSelectedItems
    };
};
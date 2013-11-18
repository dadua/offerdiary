var it = it || {};
it.profile = it.profile || {};
it.profile.notification= it.profile.notification|| {};

it.profile.notification.view = function () {
    var _get$ = function (notificationConfigVO) {
        var _notificationConfig$ = $('.notification_config_template').clone();
        notificationConfigVO = $.extend({
            weeklySummary: false,
            promotions: false,
            offerExpiryAlert: true,
            offerExpiryAlertDays: 5
        }, notificationConfigVO);

        _notificationConfig$.removeClass('notification_config_template').addClass('notificationConfig');
        _notificationConfig$.find('.weeklySummary').html(notificationConfigVO.weeklySummary?'yes':'no');
        _notificationConfig$.find('.promotions').html(notificationConfigVO.promotions?'yes':'no');
        _notificationConfig$.find('.offerExpiryAlert').html(notificationConfigVO.offerExpiryAlert?'yes':'no');
        _notificationConfig$.find('.offerExpiryAlertDays').html(notificationConfigVO.offerExpiryAlertDays);
        return _notificationConfig$;
    };

    var _saveHandler = function (e) {
        var notificationConfig$ = $('.notificationConfig'),
            weeklySummary = notificationConfig$.find('.weeklySummaryCbox').prop('checked'),
            promotions = notificationConfig$.find('.promotionsCbox').prop('checked'),
            offerExpiryAlert = notificationConfig$.find('.offerExpiryAlertCbox').prop('checked'),
            offerExpiryAlertDays = notificationConfig$.find('.offerExpiryAlertDaysTxt').val(),
            notificationConfigVO = { 
                weeklySummary: weeklySummary,
                promotions: promotions,
                offerExpiryAlert: offerExpiryAlert,
                offerExpiryAlertDays: offerExpiryAlertDays
            };

        $.post('updateUserNotificationConfig.do', {userNotificationConfig: JSON.stringify(notificationConfigVO)}, function (data) {
            it.profile.notification.refresh();
        });

    };

    var _copyValsToEditCols = function () {
        $('.editColumn').each(function(){
            var this$ = $(this),
                val = this$.parent().find('.valColumn').html();

            var input$ = this$.find('input'),
                inputType = input$.attr('type');
            if (inputType == 'text') {
                input$.val(val); 
            } else if (inputType == 'checkbox') {
                if (val === 'yes') {
                    input$.prop('checked', true);
                } else {
                    input$.prop('checked', false);
                }
            }
        });

    };

    var _cancelHandler = function (e) {
        $('.editColumn').hide();
        $('.valColumn').show();
        $('.saveCancel').addClass('hide');
        $('.editBtn').show();

    };

    var _editHandler =  function (e) {
        $('.editColumn').show();
        $('.valColumn').hide();
        $('.saveCancel').removeClass('hide');
        $('.editBtn').hide();
        _copyValsToEditCols();
    };


    var _addHandlers = function () {
        $('.editBtn').click(_editHandler);
        $('.saveBtn').click(_saveHandler);
        $('.cancelBtn').click(_cancelHandler);
        _cancelHandler();
    };



    return {
        get$: _get$,
        addHandlers: _addHandlers
    };
}();

it.profile.notification.plot = function (notificationConfigVO) {
    var notificationConfig$ = this.view.get$(notificationConfigVO);
    $('#profileContainer').html(notificationConfig$);
    this.view.addHandlers();
};

it.profile.notification.refresh = function () {
    var that = this;
    $.getJSON('getUserNotificationConfig.do', function (data) {
        var notificationConfigVO = data.result;
        that.plot (notificationConfigVO);
    });
};

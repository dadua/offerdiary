var it = it || {};

it.actionInfo = function () {
    var _view = {
        getContainerSel: function () {
            return '#actionInfoContainer';
        },
        getLoadingRowSel: function () {
            return '#loadingActionRow';
        },
        getActionMsgRowSel: function () {
            return '#actionInfoRow';
        },
        getActionMsgDivSel: function () {
            return '#alertMessage';
        }
    };

    //Defaults..
    var DEFAULT_HIDE_INTERVAL_MILLIS = 5000,
        LOGLEVEL_TO_ALERT_CLASS_MAP = {
            'success': 'alert-success',
            'error': 'alert-error',
            'info': 'alert-info'
        };

    var _init = function () {
        $(_view.getActionMsgRowSel()).find('.hideAlert').click(function () {
            $(this).parent().addClass('invisible');
        });
    },
    _getAlertClassForLogLevel = function (logLevel) {
        var alertClass = LOGLEVEL_TO_ALERT_CLASS_MAP[logLevel];
        if (alertClass) {
            alertClass = '';
        }
        return alertClass;
    },
    _showActionMsg = function (msg, logLevel, hideIntervalSeconds) {
        $(_view.getActionMsgRowSel()).removeClass('invisible');
        $(_view.getActionMsgDivSel()).html(msg).addClass(_getAlertClassForLogLevel(logLevel));
    },
    _hideActionMsg = function () {
        $(_view.getActionMsgRowSel()).addClass('invisible');
    };

    _init();

    return {
        init: _init,
        showLoading: function () {
            $(_view.getLoadingRowSel()).removeClass('invisible');
        },
        hideLoading: function () {
            $(_view.getLoadingRowSel()).addClass('invisible');
        },
        showActionMsg: function(msg, logLevel, hideIntervalSeconds) {
            _showActionMsg(msg, logLevel, hideIntervalSeconds);
        },
        hideActionMsg: function () {
            _hideActionMsg();
        },
        showSuccessActionMsg : function(msg, hideIntervalSeconds) {
            _showActionMsg(msg, 'success', hideIntervalSeconds);
        },
        showErrorActionMsg: function (msg, hideIntervalSeconds) {
            _showActionMsg(msg, 'error', hideIntervalSeconds);
        },
        showInfoActionMsg: function (msg, hideIntervalSeconds) {
            _showActionMsg(msg, 'info', hideIntervalSeconds);
        }
    };
}();

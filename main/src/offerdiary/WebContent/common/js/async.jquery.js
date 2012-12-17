/**
 * Handles various functionalities dependent on ajax calls..
 *  1) handles inprogress display when an ajax call is in progress..
 *      o to avoid the inprogress display and make an ajax call..
 *          - use $.async.silentAjax({
 *                          type: 'GET|POST', //Default get
 *                          url: 'getItems.do'
 *                      });
 *  2) Handles error and success messages by displaying it in the actionsPanel..
 *      o To determine error 
 *          - ajax HTTP call failed..
 *              for this case the error would be handled displayed based on status codes..
 *          - for a successful http ajax, the response result had an result.success != true..
 *              registration of the urls would be required..
 *      o For success messages to be displayed in the actionsPanel
 *          - the urls that have been registered with a default msg would be considered..
 *          - first preference would be given to the response msg if available,
 *              and then fallback to the default messages registered.. 
 *          
 */
(function ($) {

    //Default
    var elemSelector = 'body';

    var urlsToMonitorCache = {};

    var _ajaxStartHandler = function () {
        $('#loadingActionRow').removeClass('invisible');
        //$('#actionInfoRow').removeClass('invisible');
    };

    var _ajaxStopHandler = function () {
        $('#loadingActionRow').addClass('invisible');
        //$('#actionInfoRow').addClass('invisible');
    };

    var _getUrlPath = function (url) {
        if (url && url.indexOf('?') === -1) {
            return url;
        }
        var execs = /(.*)\?/.exec(url);
        return execs!== null ? execs[1] : null;
    };

    var _getResponse = function (xhr) {
        return $.parseJSON(xhr.responseText);
    };

    var _getResponseMsg = function (resp) {
        return resp.msg;
    };

    /**
     * Handle the error display case.. 
     */
    var _ajaxCompleteHandler = function (e, xhr, settings) {
        var paramStrippedUrl = _getUrlPath(settings.url),
            defaultMsgsForUrl = urlsToMonitorCache[paramStrippedUrl],
            isHttpSuccess = (xhr.status === 200),//Handle not modified and cached kind of cases??
            isSuccess = isHttpSuccess,
            msgToDisplay = '',
            isRegistered = false; 

        if (isHttpSuccess) {

            //Handle only registered url paths..
            if (typeof paramStrippedUrl === 'string' &&
                $.isPlainObject(defaultMsgsForUrl) &&
                typeof defaultMsgsForUrl.success === 'string') {

                isRegistered = true;

                var resp = _getResponse (xhr),
                    respMsg = _getResponseMsg(resp);

                isSuccess = resp.success;

                if (typeof respMsg === 'string') {
                    msgToDisplay = respMsg;
                } else if (isSuccess) {
                    msgToDisplay = defaultMsgsForUrl.success;
                } else {
                    msgToDisplay = defaultMsgsForUrl.fail;
                }
            }
        } else {
            //No Internet connection??
            isRegistered = true;
            msgToDisplay = 'Not connected';
            isSuccess = false;
        }

        if (isRegistered) {
            $('#actionInfoRow').removeClass('invisible');
            $('#alertMessage').html(msgToDisplay);
        }
    };

    var _initAsyncHandlers = function () {
        $(elemSelector).ajaxStart(_ajaxStartHandler).ajaxStop(_ajaxStopHandler).ajaxComplete(_ajaxCompleteHandler);
        $('#actionInfoRow .hideAlert').click(function () {
            $(this).parent().addClass('invisible');
        });
    };

    $.async = $.async || {};

    $.async.init = _initAsyncHandlers;

    /**
     * settings same as $.ajax
     */
    $.async.silentAjax = function (settings) {
        settings = $.extend ({global: false}, settings);
        return $.ajax(settings);
    };

    /**
     * urlMsgMapper = {
     *      actionUrl1: {
     *                      success: 'default success message',
     *                      fail: 'default error message'
     *                  },
     *      actionUrl2: {
     *                      success: 'default message2',
     *                      fail: 'default error message'
     *                  }
     * }
     */
    $.async.registerUrlsToMonitor = function (urlMsgMapper) {
        urlsToMonitorCache = urlMsgMapper;
    };

})(jQuery);

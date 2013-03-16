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
 *      o For success messages to be displayed in the actionsPanel
 *          - response should contain the msg
 *          
 */
(function ($) {

    //Default
    var elemSelector = 'body',
        domain = 'offerdiary.com';

    var _ajaxStartHandler = function () {
        it.actionInfo.showLoading();
    };

    var _ajaxStopHandler = function () {
        it.actionInfo.hideLoading();
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
    var _ajaxSuccessHandler = function (e, xhr, settings) {
        var resp = _getResponse (xhr),
        respMsg = _getResponseMsg(resp);

        if (typeof respMsg === 'string') {
        	if (resp.success === false) {
        		it.actionInfo.showErrorActionMsg(respMsg);
        	} else {
        		it.actionInfo.showSuccessActionMsg(respMsg);	
        	}
            
        }
    };

    //TODO: change the http error detection strategy to
    //Rely on jquery for ajax http success /error detection..
    var _ajaxErrorHandler = function (e, xhr, settings, jsException) {
        //If 404 -> Not connected..
        //If 500 -> Something wrong happened at server..
        //
        var actionMsg = 'Not Connected, Retry again in sometime..';
        if (xhr.status === 500) {
            actionMsg = 'Something bad happened at '+ domain + ', contact support@' + 'offerdiary.com';
        } 
        
        it.actionInfo.showErrorActionMsg(actionMsg);
    };


    var _initAsyncHandlers = function () {
        $(elemSelector).ajaxStart(_ajaxStartHandler).ajaxStop(_ajaxStopHandler).ajaxSuccess(_ajaxSuccessHandler).ajaxError(_ajaxErrorHandler);
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


})(jQuery);

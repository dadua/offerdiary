/**
 * Handles loading during async calls..
 */
(function ($) {

    //Default
    var elemSelector = 'body';

    var _ajaxStartHandler = function () {
        $('#actionInfoRow').removeClass('invisible');
    };

    var _ajaxStopHandler = function () {
        $('#actionInfoRow').addClass('invisible');
    };

    var _initAsyncHandlers = function () {
        $(elemSelector).ajaxStart(_ajaxStartHandler).ajaxStop(_ajaxStopHandler);
    };

    $.async = $.async || {};
    $.async.init = _initAsyncHandlers;

})(jQuery);

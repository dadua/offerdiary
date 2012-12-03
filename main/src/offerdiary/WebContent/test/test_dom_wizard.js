/**
 *
 */

var jsdom = require('jsdom');
jsdom.env({
    html: "<html><body></body></html>",
    scripts: [ 'http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js' ] },
    function (err, window) {
	var $ = window.jQuery;
	$('body').append("<div class='testing'>Hello World</div>");
	console.log($(".testing").text()); // outputs Hello World });
    });
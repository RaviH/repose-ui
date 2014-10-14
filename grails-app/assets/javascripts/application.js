// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require bootstrap
//= require bootbox
//= require helper
//= require datatable/jquery.dataTables
//= require datatable/dataTables.bootstrap
//= require_self
//= require atmosphere-meteor-jquery

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

var Jabber = {
    socket: null,
    chatSubscription: null,
    transport: null,

    subscribe: function (options) {
        var defaults = {
                type: '',
                contentType: "application/json",
                shared: false,
                transport: 'websocket',
                //transport: 'long-polling',
                fallbackTransport: 'long-polling',
                trackMessageLength: true
            },
            atmosphereRequest = $.extend({}, defaults, options);
        atmosphereRequest.onOpen = function (response) {
            console.log('atmosphereOpen transport: ' + response.transport);
        };
        atmosphereRequest.onReconnect = function (request, response) {
            console.log("atmosphereReconnect");
        };
        atmosphereRequest.onMessage = function (response) {
            //console.log('onMessage: ' + response.responseBody);
            Jabber.onMessage(response);
        };
        atmosphereRequest.onError = function (response) {
            console.log('atmosphereError: ' + response);
        };
        atmosphereRequest.onTransportFailure = function (errorMsg, request) {
            console.log('atmosphereTransportFailure: ' + errorMsg);
        };
        atmosphereRequest.onClose = function (response) {
            console.log('atmosphereClose: ' + response);
        };
        Jabber.chatSubscription = Jabber.socket.subscribe(atmosphereRequest);
    },

    unsubscribe: function () {
        Jabber.socket.unsubscribe();
    },

    onMessage: function (response) {
        if (response != null) {
            var logMessages = response.responseBody;
            if ((logMessages == '')) {
                return;
            }
            $('#logContent').html(logMessages);
        } else {
            bootbox.alert("No response yet!!!");
        }
    }
};

/*
 The Jabber variable holds all JavaScript code required for communicating with the server.
 It basically wraps the functions in atmosphere.js and jquery.atmosphere.js.
 */
function subcribeToChat() {
    if (typeof atmosphere == 'undefined') {
        // if using jquery.atmosphere.js
        Jabber.socket = $.atmosphere;
    } else {
        // if using atmosphere.js
        Jabber.socket = atmosphere;
    }
    var atmosphereRequest = {
        type: 'chat',
        url: 'atmosphere/chat/12345'
    };
    Jabber.subscribe(atmosphereRequest);
}


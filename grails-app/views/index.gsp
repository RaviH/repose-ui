<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Repose UI</title>
    %{--<asset:javascript src="atmosphere-meteor-jquery.js"/>--}%
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<h2 class="sub-header">Current Repose configurations</h2>

<div id="demo_grid1"></div>

<h1>Chat</h1>

<p>
    <button id="chat-subscribe">Subscribe</button>
</p>

<div id="chat-window"></div>
<input id="chat-input" type="text"/>


    <div class="table-responsive">
    <table id="configurationTable" class="display" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>Name</th>
                <th>Version</th>
                <th>Deployed</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</div>
<script type="text/javascript">

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
            $('#chat-window').html('');
            $('button').each(function () {
                $(this).removeAttr('disabled');
            })
        },

        onMessage: function (response) {
            var data = response.responseBody;
            if ((message == '')) {
                return;
            }
            console.log(data);
            var message = JSON.parse(data);
            var $chat = $('#chat-window');
            $chat.append('message: ' + message.message + '<br/>');
            $chat.scrollTop($chat.height());
        }
    };

    /*
     The Jabber variable holds all JavaScript code required for communicating with the server.
     It basically wraps the functions in atmosphere.js and jquery.atmosphere.js.
     */
    $(document).ready(function () {
        $('#example').dataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "${g.createLink(controller: 'reposeConfig', action: 'getAllConfigs')}"
        });

        $('#button').click( function () {
            table.row('.selected').remove().draw( false );
        } );

        if (typeof atmosphere == 'undefined') {
            // if using jquery.atmosphere.js
            Jabber.socket = $.atmosphere;
        } else {
            // if using atmosphere.js
            Jabber.socket = atmosphere;
        }

        $('#chat-subscribe').on('click', function () {
            var atmosphereRequest = {
                type: 'chat',
                url: 'atmosphere/chat/12345'
            };
            Jabber.subscribe(atmosphereRequest);
            $(this).attr('disabled', 'disabled');
            $('#chat-input').focus();
        });

        $('#chat-input').keypress(function (event) {
            if (event.which === 13) {
                event.preventDefault();
                var data = {
                    type: 'chat',
                    message: $(this).val()
                };
                Jabber.chatSubscription.push(JSON.stringify(data));
                $(this).val('');
            }
        });

        $('#unsubscribe').on('click', function () {
            Jabber.unsubscribe();
        });
    });
</script>
</body>
</html>

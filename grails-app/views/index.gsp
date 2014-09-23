<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Repose UI</title>
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<h2 class="sub-header">Current Repose configurations</h2>

<div class="table-responsive">
    <table id="configurationsTable" class="display" cellspacing="0" width="100%">
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
<script>
    var configTable;

    function saveConfig() {
        var configName = $('#configName').text();
        var configContent = $('#configContent').val();
        $.ajax({
            type: "PUT",
            dataType: "json",
            contentType: "application/json",
            url: "${g.createLink(controller: 'reposeConfig', action: 'saveConfig')}",
            data: JSON.stringify({'configName' : configName, 'configContent' : configContent}),
            error: function (request, status, error) {
                showErrorMessage("System error occurred while saving repose configuration for " + configName);
            },
            success: function (data) {
                if (data.response === 'success') {
                    Jabber.chatSubscription.push(JSON.stringify("Saved successfully!!!"));
                    return true;
                } else {
                    showErrorMessage(data.errorMessage);
                    return false;
                }
            }
        });
        return false;
    }

    function popupConfigContent(name, data) {
        bootbox.dialog({
            title: name,
            message:
                '<div id="configurationContentDialog" class="entireWindow">' +
                '   <form class="form-horizontal">' +
                '       <div class="form-group">' +
                '           <label id="configName" hidden="hidden">' + name + '</label>' +
                '           <label id="configContentLabel" class="control-label" style="float: left;" for="configContent">Config Content</label>' +
                '           <div class="col-md-11">' +
                '               <textarea class="col-md-11" id="configContent">' + data + '</textarea>' +
                '           </div>' +
                '           <div class="col-md-12">' +
                '               <textarea class="col-md-12" id="logContent" disabled></textarea>' +
                '           </div>' +
                '       </div>' +
                '   </form>' +
                '</div>',
            onEscape: function() { $(this).modal('hide') },
            buttons: {
                success: {
                    label: "Save", className: "btn-success", callback: saveConfig
                }
            }
        }).find("div.modal-dialog").addClass("entireWindow");
    }

    function showConfigurationContent(name) {
        $.ajax({
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            url: "${g.createLink(controller: 'reposeConfig', action: 'getConfigFor')}",
            data: {'configName' : name},
            error: function (request, status, error) {
                showErrorMessage("System error occurred while getting repose configuration for " + name);
            },
            success: function (data) {
                if (data.response === 'success') {
                    popupConfigContent(name, data.data)
                } else {
                    showErrorMessage(data.errorMessage);
                }
            }
        });
    }

    $(document).ready(function () {
        configTable = $('#configurationsTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "${g.createLink(controller: 'reposeConfig', action: 'getAllConfigs')}"
        });

        $('#configurationsTable tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                configTable.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                var rowData = configTable.row( this ).data();
                showConfigurationContent(rowData[0]);
            }
        });

        $('a[name=addConfig]').click(function (event) {
            event.preventDefault();
            bootbox.alert("Coming to a screen near you....soon!!!")
        });

        subcribeToChat();
    });
</script>
</body>
</html>

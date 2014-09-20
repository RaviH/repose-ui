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

    <div id="configurationContentDialog" class="entireWindow" hidden="hidden">
        <form class="form-horizontal">
            <div class="form-group">
                <label id="configContentLabel" class="control-label" style="float: left;" for="configContent">Config Content</label>
                <div class="col-md-11">
                    <textarea class="col-md-11" id="configContent"></textarea>
                </div>
            </div>
        </form>
    </div>
</div>
</div>
<script>
    function popupConfigContent(name, data) {
        $('#configContent').text(data);
        bootbox.dialog({
            title: name,
            message: $('#configurationContentDialog').html(),
            buttons: {
                success: {
                    label: "Save",
                            className: "btn-success",
                            callback: function () {
                        var name = $('#name').val();
                        var answer = $("input[name='awesomeness']:checked").val()
                        Example.show("Hello " + name + ". You've chosen <b>" + answer + "</b>");
                    }
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
        var table = $('#configurationsTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "${g.createLink(controller: 'reposeConfig', action: 'getAllConfigs')}"
        });

        $('#configurationsTable tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                var rowData = table.row( this ).data();
                showConfigurationContent(rowData[0]);
            }
        });
    });
</script>
</body>
</html>

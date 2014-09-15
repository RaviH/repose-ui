<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Repose UI</title>
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<h2 class="sub-header">Current Repose configurations</h2>

<div id="demo_grid1"></div>

<div class="table-responsive">
    <table id="example" class="display" cellspacing="0" width="100%">
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
    $(document).ready(function () {
        $('#example').dataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "${g.createLink(controller: 'reposeConfig', action: 'getAllConfigs')}"
        });
    });
</script>
</body>
</html>

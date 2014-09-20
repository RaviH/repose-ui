<!DOCTYPE html>
<head>
    %{--Copied from Twitter Bootstrap--}%
    %{--http://getbootstrap.com/examples/dashboard/--}%
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="template for the app">
    <meta name="author" content="Ravi Hasija">

    <title><g:layoutTitle default="Repose UI"/></title>

    %{--Including bootstrap.css -- copied from their website.
        TODO: want to use actually bootstrap plugin css
    --}%
    <asset:stylesheet src="bootstrap.css"/>

    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>
</head>

<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Repose Configurations</a>
        </div>

        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${createLink(controller: reposeConfig, action: getAllConfigs)}">List configurations</a></li>
                <li><a href="#">Add configuration</a></li>
            </ul>

            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="${createLink(controller: reposeConfig, action: getAllConfigs)}">List configurations</a></li>
                <li><a href="#">Add configuration</a></li>
            </ul>
        </div>
    </div>
</div>
<g:layoutBody/>
</body>
</html>

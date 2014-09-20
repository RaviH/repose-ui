package org.repose

import grails.converters.JSON

class ReposeConfigController {
    ReposeService reposeService
    def propertiesToRender = ['name', 'version', 'isDefault']

    def index() {
    }

    def setDefaultFor() {
        def responseMap = [response: 'success']
        try {
            String reposeConfigFileName = request?.JSON?.reposeConfigFileName
            int version = request?.JSON?.version as Integer
            reposeService.setDefault(reposeConfigFileName, version)
            //TODO: Also write the file to the file store
        } catch (Exception e) {
            responseMap.response = 'failure'
            responseMap.errorMessage = "Error occurred while setting default for $reposeConfigFileName to version: $version"
        }
        render responseMap as JSON
    }

    def allReposeConfigVersionsFor(String reposeConfigName) {
        def reposeConfigCommands = reposeService.allConfigFilesFor(reposeConfigName)
        def dataToRender = prepareResponseMap(reposeConfigCommands)
        println "allReposeConfigVersionsFor(): Data will be rendered: " + (dataToRender as JSON)
        render dataToRender as JSON
    }

    // Render template details (when a user clicks on a row to view the template details)
    def showConfig(String name, Integer version) {
        def configContent = reposeService.configFor(name, version)
        def record = [content: configContent]
        render record as JSON
    }

    def getConfigFor(String configName) {
        def responseMap = [:]
        if (configName) {
            try {
                def data = new File("/etc/repose/$configName").readLines()
                responseMap.response = 'success'
                responseMap.data = data.join("\n")
            } catch (FileNotFoundException ie) {
                responseMap.errorMessage = "Could not find a corresponding config file"
            } catch (IOException ie) {
                responseMap.errorMessage = "Could not read the config file"
            }
        } else {
            responseMap.errorMessage = "Please provide a config name"
        }
        render responseMap as JSON
    }

    def getAllConfigs() {
        def dataToRender
        try {
            def searchString = params.get("search[value]")
            def sorting = params.get("order[0][column]")
            def sortingDir = params.get("order[0][dir]")
            if (searchString) {
                // Refresh data only if forceRefresh is set and force refresh will be set
                // when a template is updated and we have a filter value set in the search box.
                dataToRender = performSearchOnLatestReposeConfigs(searchString)
            } else {
                List<ReposeConfigCommand> commands = reposeService.getAllConfigFiles()
                dataToRender = sortData(commands, propertiesToRender[sorting as Integer], sortingDir == "asc" ? true : false )
            }
        } catch (Exception e) {
            dataToRender = [errorMessage: 'Error occurred while querying the database to get the configs']
        }
        render dataToRender as JSON
    }

    private sortData(List<ReposeConfigCommand> commands, String sortColumn, boolean ascending) {
        if (ascending) {
            prepareResponseMap(commands.sort { a, b -> a."$sortColumn" <=> b."$sortColumn" })
        } else {
            prepareResponseMap(commands.sort { a, b -> b."$sortColumn" <=> a."$sortColumn" })
        }
    }

    private def performSearchOnLatestReposeConfigs(String searchString) {
        List<ReposeConfigCommand> reposeConfigCommands = reposeService.getAllConfigFiles()
        def matchingReposeConfigs = reposeConfigCommands.findAll { ReposeConfigCommand reposeConfigCommand ->
            reposeConfigCommand.name.toLowerCase().contains(searchString.toLowerCase())
        }
        prepareResponseMap(matchingReposeConfigs)
    }


    private def prepareResponseMap(List<ReposeConfigCommand> reposeConfigs) {
        def dataToRender = [draw: params.draw, aaData: []]
        reposeConfigs.each { ReposeConfigCommand reposeConfigCommand ->
            def data = []
            propertiesToRender.each { data.add(reposeConfigCommand."${it}") }
            dataToRender.aaData << data
        }
        dataToRender.recordsTotal = dataToRender.aaData.size()
        dataToRender.iTotalRecords = dataToRender.aaData.size()
        paginateConfigurationsList(dataToRender)
        dataToRender.recordsFiltered = dataToRender.iTotalRecords
        dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
        dataToRender
    }

    private def paginateConfigurationsList(def dataToRender) {
        def aaDataSize = dataToRender.aaData.size()
        def startIndex = params.start as Integer

        if (startIndex != null) {
            int endIndex = getEndIndex(startIndex, aaDataSize)
            dataToRender.aaData = dataToRender.aaData.subList(startIndex, endIndex)
        }
    }

    private def getEndIndex(int startIndex, aaDataSize) {
        def paginationEndIndex = startIndex + (params.length as Integer)
        aaDataSize < paginationEndIndex || paginationEndIndex == -1 ? aaDataSize : paginationEndIndex
    }
}
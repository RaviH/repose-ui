package org.repose

class ReposeConfig {
    String id
    String configName
    String fileName
    String baseFilePath
    String absoluteFilePath
    int defaultVersion
    List<ReposeConfigFile> configFileList

    static constraints = {
    }
    static hasMany = [configFileList:ReposeConfigFile]
    static mapping = {
        configFileList sort: 'version', order: 'desc'
    }
}

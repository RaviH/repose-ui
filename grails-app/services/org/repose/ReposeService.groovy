package org.repose

import com.google.common.io.Files
import grails.transaction.Transactional

@Transactional
class ReposeService {
    static final String BASE_CONFIG_DIR = "/etc/repose"
    private final static int BASE_VERSION = 1

    private List<ReposeConfig> getAllConfigsIn(String directoryPath = "/etc/repose") {
        FileUtil.allFilesInDirectory(directoryPath)
    }

    private boolean isDbEmpty() {
        ReposeConfig.count == 0
    }

    def setupDataFirstTime(String directoryPath = "/etc/repose") {
        if (isDbEmpty()) {
            getAllConfigsIn(directoryPath).each { ReposeConfig reposeConfig ->
                reposeConfig.save(flush:true, failOnError: true)
            }
        }
    }

    def getAllConfigFiles() {
        List<ReposeConfigCommand> configCommands = []
        ReposeConfig.findAll().each { ReposeConfig reposeConfig ->
            reposeConfig.configFileList.each { ReposeConfigFile reposeConfigFile ->
                configCommands << new ReposeConfigCommand(name: reposeConfig.fileName, version: reposeConfigFile.version, isDefault: reposeConfig.defaultVersion)
            }
        }
        configCommands
    }

    def saveConfigFile(String configName, String configContent) {
        log.info("File content for $configName:\n$configContent")
        def responseMap = [response: 'success']
        def timeStamp = new Date().format("yyyy.MM.dd_HH.mm.ss")
        try {
            // Take backup
            String sourceFileName = "$BASE_CONFIG_DIR/$configName"
            String backupFileName = "$BASE_CONFIG_DIR/${configName}.backup.${timeStamp}"
            Files.copy(new File(sourceFileName), new File(backupFileName))
            // Remove the sourceFile
            new File(sourceFileName).delete()
            // Create a new file with updated content
            def newFile = new File(sourceFileName)
            newFile << configContent
            saveConfigToDB(configName, configContent)
        } catch (FileNotFoundException fe) {
            log.error(fe)
            responseMap.errorMessage = "Could not find a corresponding config file"
        } catch(IOException ie) {
            log.error(ie)
            responseMap.errorMessage = "Could not read the config file"
        }
        return responseMap
    }

    def saveConfigToDB(String configName, String configContent) {
        ReposeConfig reposeConfig = ReposeConfig.findByConfigName(configName)
        int latestVersion = reposeConfig.configFileList[0].version
        reposeConfig.configFileList << new ReposeConfigFile(fileContent: configContent, version: latestVersion + 1 )
        reposeConfig.save(flush: true, failOnError: true)
    }

    def allConfigFilesFor(String fileName) {
        List<ReposeConfigCommand> configCommands = []
        ReposeConfig reposeConfig = ReposeConfig.findByFileName(fileName)
        reposeConfig.configFileList.each { ReposeConfigFile reposeConfigFile ->
            configCommands << new ReposeConfigCommand(name: reposeConfig.fileName, version: reposeConfigFile.version, isDefault: reposeConfig.defaultVersion)
        }
        configCommands
    }

    def setDefault(String fileName, int version) {
        ReposeConfig reposeConfig = ReposeConfig.findByFileName(fileName)
        reposeConfig.version = version
        reposeConfig.save(flush: true, failOnError: true)
    }

    def configFor(String fileName, int version) {
        def reposeConfig = ReposeConfig.findByFileName(fileName)
        reposeConfig.configFileList.find {ReposeConfigFile reposeConfigFile -> reposeConfigFile.version == version }.fileContent
    }
}
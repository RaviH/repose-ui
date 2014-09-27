import org.codehaus.groovy.grails.commons.GrailsApplication
import org.repose.ReposeService
import sun.net.www.ApplicationLaunchException

class BootStrap {
    ReposeService reposeService
    GrailsApplication grailsApplication

    def init = { servletContext ->
        validateReposeConfigDir()
        validateReposeLogFile()
    }

    def validateReposeConfigDir() {
        def reposeConfigDirPath = grailsApplication.config.reposeConfigDir
        if (reposeConfigDirPath) {
            def reposeConfigDir = new File(reposeConfigDirPath)
            if (reposeConfigDir.exists() && reposeConfigDir.canRead() && reposeConfigDir.canWrite()) {
                log.info("Repose configuration directory $reposeConfigDirPath found and is in good order!")
            } else {
                throw new ApplicationLaunchException("Repose configuration directory either does not exist, is not readable or is not writable.")
            }
        } else {
            throw new ApplicationLaunchException("Please provide repose config directory property ('reposeConfigDir') in application properties")
        }
    }

    def validateReposeLogFile() {
        def reposeLogFilePath = grailsApplication.config.reposeLogFile
        if (reposeLogFilePath) {
            def reposeLogFile = new File(reposeLogFilePath)
            def reposeLogDir = new File(reposeLogFile.parent)
            if (!reposeLogFile.exists() || !reposeLogFile.canRead()) {
                throw new ApplicationLaunchException("Repose log file either does not exist or is not readable OR you ATE it!!!")
            } else if (!reposeLogDir.canWrite()) {
                throw new ApplicationLaunchException("Repose log directory -- cannot create repose ui logs in there...you know!")
            }
            else {
                log.info("Repose log file: $reposeLogFilePath found!!!")
            }
        } else {
            throw new ApplicationLaunchException("Please provide repose log file property ('reposeLogFile') in application properties")
        }
    }

    def destroy = {
    }
}

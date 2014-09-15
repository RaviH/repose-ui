import org.repose.ReposeService

class BootStrap {
    ReposeService reposeService

    def init = { servletContext ->
        reposeService.setupDataFirstTime("/etc/repose")
    }
    def destroy = {
    }
}

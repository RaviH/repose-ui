package org.repose

import grails.util.Holders
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.DefaultBroadcaster

class LogFileUpdateService {
    static final String LOG_FILE_NAME = "/var/log/repose/current.log"
    def atmosphereMeteor = Holders.applicationContext.getBean("atmosphereMeteor")
    boolean stop = false
    static scope = "prototype"

    def stop() {
        stop = true
    }

    def tail(Closure c) {
        def file = new File(LOG_FILE_NAME)
        def runnable = {
            int length = file.length()
            def reader = file.newReader()
            try {
                reader.skip(length)

                def line
                while (!stop) {
                    line = reader.readLine()
                    log.debug("Line is empty!")
                    if (line) {
                        log.info("Line read from file: $line")
                        c.call(line)
                    } else {
                        Thread.currentThread().sleep(500)
                    }
                }
            }
            finally {
                reader?.close()
            }
        } as Runnable

        def t = new Thread(runnable)
        t.start()
    }

    def handleLog(String mapping) {
        def message = ""
        Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, mapping)

        tail({ message += " $it\n" })
        int i = 20
        while (1) {
            if (i == 0 || hasReposeFinishedUpdating(message?.toLowerCase())) {
                b.broadcast(message ?: "Ending the wait...")
                break
            } else if (!message) {
                b.broadcast("Waiting for Repose to update for $i seconds...\n")
            } else {
                b.broadcast(message)
            }
            i--
            Thread.sleep(1000)
        }
        stop()
        return message
    }

    def hasReposeFinishedUpdating(String message) {
        message.contains("repose Ready") ||
        message.contains("updated file")
    }
}

package org.repose

class TailFileService {
    boolean stop = false
    File file

    TailFileService(String fileName) {
        file = new File(fileName)
    }

    def stop() {
        stop = true
    }

    def tail(Closure c) {
        def runnable = {
            def reader

            try {
                reader = file.newReader()
                reader.skip(file.length())

                def line

                while (!stop) {
                    line = reader.readLine()

                    if (line) {
                        println "Line read from file: $line"
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

    def static stopWhenFound(String fileName, String textToLookFor) {
        assert fileName, "Please provide absolute file path and name to do tail on"
        assert textToLookFor, "Please provide the text to look for. Search is case insensitive"

        def tailFileService = new TailFileService(fileName)
        def message = "\n"
        tailFileService.tail({ message += " $it"})
        int i = 1
        while (1) {
            if (i == 20 ||
                message.toLowerCase().contains("Repose ready".toLowerCase()) ||
                message.toLowerCase().contains("...".toLowerCase()) ||
                message.toLowerCase().contains("Updated file".toLowerCase())) {
                break
            }
            i++
            Thread.sleep(1000)
        }
        tailFileService.stop()
        return message
    }
}

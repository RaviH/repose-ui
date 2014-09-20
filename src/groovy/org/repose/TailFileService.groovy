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
        while (1) {
            if (message.toLowerCase().contains(textToLookFor.toLowerCase())) {
                break
            }
            Thread.sleep(1000)
        }
        tailFileService.stop()
        return message
    }
}

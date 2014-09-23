package org.repose

import grails.util.Holders
import org.atmosphere.cpr.*
import org.atmosphere.websocket.WebSocketEventListenerAdapter

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.WEBSOCKET

class LogFileMeteorHandler extends HttpServlet {

    def atmosphereMeteor = Holders.applicationContext.getBean("atmosphereMeteor")
    def atmosphereTestService = Holders.applicationContext.getBean("atmosphereTestService")

    @Override
    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapping = "/atmosphere/chat" + request.getPathInfo()
        Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, mapping, true)
        Meteor m = Meteor.build(request)

        if (m.transport().equals(WEBSOCKET)) {
            m.addListener(new WebSocketEventListenerAdapter() {
                @Override
                void onDisconnect(AtmosphereResourceEvent event) {
                    atmosphereTestService.sendDisconnectMessage(event, request)
                }
            })
        } else {
            m.addListener(new AtmosphereResourceEventListenerAdapter() {
                @Override
                void onDisconnect(AtmosphereResourceEvent event) {
                    atmosphereTestService.sendDisconnectMessage(event, request)
                }
            })
        }

        m.setBroadcaster(b)
    }

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapping = "/atmosphere/chat" + request.getPathInfo();
        LogFileUpdateService logFileUpdateService = Holders.applicationContext.getBean("logFileUpdateService")
        logFileUpdateService.handleLog(mapping)
    }
}

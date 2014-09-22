package org.repose

import org.atmosphere.cpr.AtmosphereResourceEvent
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.DefaultBroadcaster
import org.atmosphere.cpr.Meteor

import grails.converters.JSON
import org.repose.TailFileService

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.atmosphere.websocket.WebSocketEventListenerAdapter
import grails.util.Holders
import static org.atmosphere.cpr.AtmosphereResource.TRANSPORT.WEBSOCKET

class ChatMeteorHandler extends HttpServlet {

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

		String message = TailFileService.stopWhenFound("/var/log/repose/current.log", "Repose Ready")

        if (message == null) {
			atmosphereTestService.recordIncompleteMessage()
		} else {
			if (message.toLowerCase().contains("<script")) {
				atmosphereTestService.recordMaliciousUseWarning(message)
			} else {
                atmosphereTestService.recordChat(message)
				Broadcaster b = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, mapping)
                b.broadcast(message)
			}
		}
	}
}

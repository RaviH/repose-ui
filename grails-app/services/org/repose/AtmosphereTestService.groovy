package org.repose

import grails.converters.JSON

class AtmosphereTestService {
	def atmosphereMeteorService

	def recordChat(data) {
		// This method could be used to persist chat messages to a data store.
		log.info("AtmosphereTestService.recordChat: ${data}")
	}

	def recordIncompleteMessage() {
		// This method could be used to persist errors to a data store.
        log.info("Error AtmosphereTestService.recordIncompleteMessage")
	}

	def recordMaliciousUseWarning(data) {
		// This method could be used to persist potential malicious code to a data store.
        log.info("Warning AtmosphereTestService.recordMaliciousUseWarning: ${data}")
	}

	def sendDisconnectMessage(event, request) {
		// This method could be used to send chat participants a message that a user has left.
		def message = "A user has left the chat session"
        log.info(message)
		event.broadcaster().broadcast(message)
	}
}


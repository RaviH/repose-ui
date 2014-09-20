import org.grails.plugins.atmosphere_meteor_sample.ChatMeteorHandler

def defaultMapping = "/atmosphere/*"

def defaultInitParams = [
        "org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
        "org.atmosphere.cpr.AtmosphereInterceptor": """
			org.atmosphere.client.TrackMessageSizeInterceptor,
			org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor,
			org.atmosphere.interceptor.HeartbeatInterceptor
		"""
]

servlets = [
        MeteorServletChat: [
                className: "org.grails.plugins.atmosphere_meteor_sample.DefaultMeteorServlet",
                mapping: "/atmosphere/chat/*",
                handler: ChatMeteorHandler,
                initParams: defaultInitParams
        ]
]
import org.repose.ChatMeteorHandler

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
                className: "org.repose.DefaultMeteorServlet",
                mapping: "/atmosphere/chat/*",
                handler: ChatMeteorHandler,
                initParams: defaultInitParams
        ]
]

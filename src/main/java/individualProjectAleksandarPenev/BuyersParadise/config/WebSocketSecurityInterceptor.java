package individualProjectAleksandarPenev.BuyersParadise.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketSecurityInterceptor implements ChannelInterceptor{

    private final SimpUserRegistry simpUserRegistry;

    @Autowired
    public WebSocketSecurityInterceptor(SimpUserRegistry simpUserRegistry){
        this.simpUserRegistry = simpUserRegistry;
    }

    Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @EventListener(WebSocketSession.class)
    public void handleWebSocketSession(WebSocketSession event) {
        logger.info("WebSocketSession: " + simpUserRegistry.getUserCount());
    }

    @EventListener(SessionSubscribeEvent.class)
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        logger.info("SessionSubscribeEvent: " + simpUserRegistry.getUserCount());

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        logger.info("SessionSubscribeEvent sha: " + sha.getSessionId());
    }

    @EventListener(SessionConnectedEvent.class)
    public void handleSessionConnectedEvent(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection : " + simpUserRegistry.getUserCount());
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        logger.info("session closed : " + simpUserRegistry.getUserCount());
    }


}

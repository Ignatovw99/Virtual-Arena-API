package com.virtualarena.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketOAuth2SecurityChannelInterceptor implements ChannelInterceptor {

    private static final String AUTH_MESSAGE_HEADER = "X-Authorization";

    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (Objects.isNull(headerAccessor) ||
                !StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            return message;
        }

        String accessToken = extractAccessToken(headerAccessor);
        Jwt jwt = jwtDecoder.decode(accessToken);
        Authentication authentication = jwtAuthenticationConverter.convert(jwt);
        headerAccessor.setUser(authentication);

        return message;
    }

    private String extractAccessToken(StompHeaderAccessor headerAccessor) {
        List<String> authorization = headerAccessor.getNativeHeader(AUTH_MESSAGE_HEADER);
        if (Objects.isNull(authorization)) {
            return "";
        }
        return authorization.get(0).split(" ")[1];
    }
}

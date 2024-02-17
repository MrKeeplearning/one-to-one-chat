package org.example.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /*
          "/user" 라는 prefix를 가진 목적지로 보내진 메시지를 클라이언트에게 다시 전달해주는 역할을 하는 simple broker를 활성화
          클라이언트는 "/user"로 시작하는 주소를 구독해서 해당 주소로 보내진 메시지를 받을 수 있게 된다.
         */
        registry.enableSimpleBroker("/user");

        /*
            "/app"이라는 prefix를 설정함으로써, Controller 클래스의 @MessageMapping 어노테이션이 붙은 메서드로 라우팅될 메시지의
            목적지를 설정하는데 사용된다.
            클라이언트가 '/app/hello' 로 메시지를 보내면 @MessageMapping("/hello")가 붙은 메서드가 해당 메시지를 처리한다.
         */
        registry.setApplicationDestinationPrefixes("/app");

        /*
            설정된 /user prefix는 사용자 전용 메시지를 구별하는데 사용된다.
            클라이언트가 /user/123/mymessage 라는 주소로 메시지를 보내면,해당 메시지는 오직 123이라는 identifier를 가진 사용자만 받을 수 있다.
         */
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 클라이언트가 WebSocket 서버에 연결할 수 있는 HTTP URL을 설정한다.
     * 클라이언트는 이 URL을 통해 WebSocket 연결을 초기화하고, 이후에는 이 연결을 통해 양방향 데이터 통신이 가능해진다.
     *
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOriginPatterns("http://localhost:8080", "http://localhost:8081")
                .withSockJS();
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }
}
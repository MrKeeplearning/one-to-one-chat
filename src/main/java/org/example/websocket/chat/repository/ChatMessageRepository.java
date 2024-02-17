package org.example.websocket.chat.repository;

import java.util.List;
import org.example.websocket.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByChatRoomId(String s);
}

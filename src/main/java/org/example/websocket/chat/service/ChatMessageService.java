package org.example.websocket.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.chat.domain.ChatMessage;
import org.example.websocket.chat.repository.ChatMessageRepository;
import org.example.websocket.chatroom.service.ChatRoomService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    // DTO를 사용해서 주고 받도록 리팩토링
    public ChatMessage save(ChatMessage chatMessage) {
        String chatRoomId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(() -> new IllegalArgumentException("Chat room ID could not be created or retrieved."));
        chatMessage.setChatRoomId(chatRoomId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatRoomId = chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                false);
        return chatRoomId.map(chatMessageRepository::findByChatRoomId).orElse(new ArrayList<>());
    }
}

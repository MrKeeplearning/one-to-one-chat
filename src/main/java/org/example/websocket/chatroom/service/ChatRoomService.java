package org.example.websocket.chatroom.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.websocket.chatroom.domain.ChatRoom;
import org.example.websocket.chatroom.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(String senderId, String recipientId, boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatRoomId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        String chatRoomId = createChatRoomId(senderId, recipientId);
                        return Optional.of(chatRoomId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatRoomId(String senderId, String recipientId) {
        String chatRoomId = String.format("%s_%s", senderId, recipientId);
        ChatRoom senderRecipient = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
        ChatRoom recipientSender = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatRoomId;
    }
}

package org.example.websocket.user.repository;

import java.util.List;
import org.example.websocket.user.domain.Status;
import org.example.websocket.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByStatus(Status status);
}

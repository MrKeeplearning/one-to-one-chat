package org.example.websocket.user.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document   // MongoDB에서 JPA의 @Entity와 같은 역할을 한다.
public class User {
    @Id
    private String nickName;
    private String fullName;
    private Status status;
}

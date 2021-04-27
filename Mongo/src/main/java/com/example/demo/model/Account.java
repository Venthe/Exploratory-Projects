package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

import static lombok.AccessLevel.NONE;

@Value
@AllArgsConstructor(onConstructor_ = @PersistenceConstructor, staticName = "reconstitute")
@With
public class Account {
    @Id
    @With(NONE)
    String id;
    String anything;

    public static Account create(String anything) {
        return new Account(UUID.randomUUID().toString(), anything);
    }
}

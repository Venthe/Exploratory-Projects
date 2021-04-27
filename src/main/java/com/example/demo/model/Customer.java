package com.example.demo.model;

import com.example.demo.cascade.CascadeSave;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class Customer {
    @Id
    @Indexed
    @EqualsAndHashCode.Include
    @Getter
    CustomerNumber customerNumber;
    Other other;
    @DBRef(lazy = true)
    @CascadeSave
    Set<Account> accounts = new HashSet<>();
}

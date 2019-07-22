package eu.venthe.hibernateinitializer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class Account {
    @Id
    @GeneratedValue
    Long id;
    @NonNull
    String nrb;
    @NonNull
    String prefix;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    Customer customer;
}

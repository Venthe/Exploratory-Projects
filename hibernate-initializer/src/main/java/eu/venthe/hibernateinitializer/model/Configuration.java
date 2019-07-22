package eu.venthe.hibernateinitializer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Configuration {
    @Id
    @GeneratedValue
    Long id;
    @NonNull
    @Enumerated
    SampleEnum enumType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    Customer customer;
}

package eu.venthe.hibernateinitializer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    Long id;

    @NonNull
    String clientNumber;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    Set<Account> accounts;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    Configuration configuration;

    public Customer(String clientNumber, Set<Account> accounts, Configuration configuration) {
        this.clientNumber = clientNumber;
        this.accounts = accounts;
        this.configuration = configuration;

        accounts.forEach(account -> account.customer = this);
        configuration.customer = this;
    }
}

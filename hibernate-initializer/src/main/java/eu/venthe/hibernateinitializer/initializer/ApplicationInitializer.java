package eu.venthe.hibernateinitializer.initializer;

import eu.venthe.hibernateinitializer.CustomerRepository;
import eu.venthe.hibernateinitializer.model.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Profile("initialize")
public class ApplicationInitializer {
    private final CustomerRepository customerRepository;

    public ApplicationInitializer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

        this.initialize();
    }

    @Transactional
    public void initialize() {
        customerRepository.saveAll(
                IntStream.range(0, 1000)
                        .mapToObj(getCustomer())
                        .collect(Collectors.toSet())
        );
    }

    private IntFunction<Customer> getCustomer() {
        return i -> new CustomerBuilder()
                .setClientNumber(UUID.randomUUID().toString())
                .setAccounts(generateAccounts())
                .setConfiguration(generateConfiguration())
                .createCustomer();
    }

    private Set<Account> generateAccounts() {
        return IntStream.range(0, new Random().nextInt(10))
                .mapToObj(generateAccount())
                .collect(Collectors.toSet());
    }

    private IntFunction<Account> generateAccount() {
        return i -> new AccountBuilder()
                .setNrb(UUID.randomUUID().toString())
                .setPrefix(UUID.randomUUID().toString())
                .createAccount();
    }

    private Configuration generateConfiguration() {
        return new ConfigurationBuilder().setEnumType(getRandomSampleEnum()).createConfiguration();
    }

    private SampleEnum getRandomSampleEnum() {
        SampleEnum[] values = SampleEnum.values();
        return values[new Random().nextInt(values.length)];
    }
}

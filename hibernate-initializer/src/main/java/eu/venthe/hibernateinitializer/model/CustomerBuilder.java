package eu.venthe.hibernateinitializer.model;

import java.util.Set;

public class CustomerBuilder {
    private String clientNumber;
    private Set<Account> accounts;
    private Configuration configuration;

    public CustomerBuilder setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
        return this;
    }

    public CustomerBuilder setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
        return this;
    }

    public CustomerBuilder setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public Customer createCustomer() {
        return new Customer(clientNumber, accounts, configuration);
    }
}
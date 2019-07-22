package eu.venthe.hibernateinitializer.model;

public class AccountBuilder {
    private String nrb;
    private String prefix;

    public AccountBuilder setNrb(String nrb) {
        this.nrb = nrb;
        return this;
    }

    public AccountBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Account createAccount() {
        return new Account(nrb, prefix);
    }
}
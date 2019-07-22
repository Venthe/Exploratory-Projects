package eu.venthe.hibernateinitializer.model;

public class ConfigurationBuilder {
    private SampleEnum enumType;

    public ConfigurationBuilder setEnumType(SampleEnum enumType) {
        this.enumType = enumType;
        return this;
    }

    public Configuration createConfiguration() {
        return new Configuration(enumType);
    }
}
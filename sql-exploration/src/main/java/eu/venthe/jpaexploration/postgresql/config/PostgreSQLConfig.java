package eu.venthe.jpaexploration.postgresql.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = PostgreSQLConfig.BASE_PACKAGES,
        entityManagerFactoryRef = PostgreSQLConfig.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = PostgreSQLConfig.TRANSACTION_MANAGER_BEAN
)
@AllArgsConstructor
public class PostgreSQLConfig {
    public static final String PERSISTENCE_UNIT_NAME = "PostgreSQLPersistenceUnitName";
    static final String BASE_PACKAGES = "eu.venthe.jpaexploration.postgresql.repository";
    static final String ENTITY_MANAGER_FACTORY_BEAN = "entityManagerFactory";
    static final String TRANSACTION_MANAGER_BEAN = "transactionManager";
    private static final String DATA_SOURCE = "dataSource";
    private static final String DATA_SOURCE_PROPERTY_KEY = "spring.datasource.postgresql";
    private static final String SPRING_IMPLICIT_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy";
    private static final String SPRING_PHYSICAL_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";
    private static final String IMPLICIT_NAMING_STRATEGY = "hibernate.implicit_naming_strategy";
    private static final String PHYSICAL_NAMING_STRATEGY = "hibernate.physical_naming_strategy";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String SQL_SERVER_DIALECT = "org.hibernate.dialect.PostgreSQL95Dialect";
    private static final String MODEL_PACKAGE = "eu.venthe.jpaexploration.model";

    @Primary
    @Bean(name = DATA_SOURCE)
    @ConfigurationProperties(DATA_SOURCE_PROPERTY_KEY)
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY_BEAN)
    @DependsOn(DATA_SOURCE)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = getEntityManagerFactory();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(MODEL_PACKAGE);
        entityManagerFactory.setJpaVendorAdapter(getJpaVendorAdapter());
        entityManagerFactory.setJpaPropertyMap(getEntityManagerProperties());
        entityManagerFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);

        return entityManagerFactory;
    }

    private LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        return new LocalContainerEntityManagerFactoryBean();
    }

    private HibernateJpaVendorAdapter getJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Map<String, String> getEntityManagerProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put(IMPLICIT_NAMING_STRATEGY, SPRING_IMPLICIT_NAMING_STRATEGY);
        properties.put(PHYSICAL_NAMING_STRATEGY, SPRING_PHYSICAL_NAMING_STRATEGY);
        properties.put(HIBERNATE_DIALECT, SQL_SERVER_DIALECT);

        return properties;
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER_BEAN)
    @DependsOn(ENTITY_MANAGER_FACTORY_BEAN)
    public PlatformTransactionManager transactionManager(@Qualifier(ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = getTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    private JpaTransactionManager getTransactionManager() {
        return new JpaTransactionManager();
    }
}

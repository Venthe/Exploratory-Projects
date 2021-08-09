package eu.venthe.testcontainers;

import eu.venthe.testcontainers.model.MyEntity;
import eu.venthe.testcontainers.repository.MyEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("With JPA")
@Slf4j
@TestPropertySource(properties = {"logging.level.org.hibernate.cache=debug"})
public class JpaTest extends AbstractIntegrationTest {

    @Autowired
    MyEntityRepository myEntityRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ApplicationContext appContext;

    @Test
    @DisplayName("with automatic transaction it should load and save entity")
    void basicTest() {
        // given
        final MyEntity savedEntity = myEntityRepository.save(createEntity());
        entityManager.flush();

        // when
        entityManager.clear();
        MyEntity foundEntity = myEntityRepository.findById(savedEntity.getId())
                .orElseThrow();

        // then
        assertThat(foundEntity)
                .isNotNull()
                .satisfies(value -> {
                            assertThat(value.getId())
                                    .isEqualTo(savedEntity.getId());
                            assertThat(value.getValue())
                                    .isEqualTo(savedEntity.getValue());
                        }
                );
    }

    @Test
    @DisplayName("with disabled automatic transaction it should load and save entity")
    void transactionalTest() {
        // given
        MyEntity entity = createEntity();
        entity.setId(123L);

        // Note: JPA will always select before persist
        final MyEntity savedEntity = myEntityRepository.save(entity);
        entityManager.flush();
        entityManager.clear();

        // when
        MyEntity foundEntity = myEntityRepository.findById(savedEntity.getId())
                .orElseThrow();

        // then
        assertThat(foundEntity)
                .isNotNull()
                .satisfies(value -> {
                            assertThat(value.getId())
                                    .isEqualTo(savedEntity.getId());
                            assertThat(value.getValue())
                                    .isEqualTo(savedEntity.getValue());
                        }
                );
    }

    @Test
    @DisplayName("with manual EM it should load and save entity")
    void shouldSaveEntityViaEm() {
        // given
        MyEntity savedEntity = createEntity();
        savedEntity.setId(123L);
        entityManager.persist(savedEntity);

        // when
        entityManager.flush();
        entityManager.clear();
        MyEntity foundEntity = entityManager.find(MyEntity.class, savedEntity.getId());

        // then
        assertThat(foundEntity)
                .isNotNull()
                .satisfies(value -> {
                            assertThat(value.getId())
                                    .isEqualTo(savedEntity.getId());
                            assertThat(value.getValue())
                                    .isEqualTo(savedEntity.getValue());
                        }
                );
    }

    private static MyEntity createEntity() {
        return MyEntity.builder()
                .value(1)
                .build();
    }
}

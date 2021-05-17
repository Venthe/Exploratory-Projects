package eu.venthe.testcontainers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MyEntityRepository extends JpaRepository<MyEntity, UUID> {
}

package eu.venthe.testcontainers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class MyEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    Integer value;
}

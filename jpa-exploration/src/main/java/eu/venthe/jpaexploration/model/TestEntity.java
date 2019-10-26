package eu.venthe.jpaexploration.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "test_entity")
@Table(name = "test_entity")
@Getter
public class TestEntity {
    @Id
    @GeneratedValue(generator = "test_entity_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "test_entity_id_seq",
            sequenceName = "test_entity_id_seq",
            allocationSize = 100
    )
    private long id;

    @Column(name = "CHARACTER", nullable = false, length = 3)
    private String character;

    @Column(name = "NUMERIC", nullable = false, length = 7)
    private Long numeric;

    @Column
    private LocalDateTime datetime;
}

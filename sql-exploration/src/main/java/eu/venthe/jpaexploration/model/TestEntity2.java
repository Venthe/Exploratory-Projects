package eu.venthe.jpaexploration.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "test_entity_2")
@Table(name = "test_entity_2")
@Getter
public class TestEntity2 {
    @Id
    @GeneratedValue(generator = "test_entity_2_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "test_entity_2_id_seq",
            sequenceName = "test_entity_2_id_seq",
            allocationSize = 50
    )
    private long id;

    @Column(name = "CHARACTER", nullable = false, length = 6)
    private String character;

    @Column(name = "NUMERIC", nullable = false, length = 7)
    private Long numeric;

    @Column
    private LocalDateTime datetime;
}

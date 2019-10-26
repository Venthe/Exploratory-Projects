package eu.venthe.jpaexploration.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Test {
    @EmbeddedId
    private Identity id;

    @EqualsAndHashCode
    @ToString
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Identity implements Serializable {
        @Column(name = "ID_1", nullable = false, length = 4)
        private String id_1;

        @Column(name = "ID_2", nullable = false, length = 4)
        private String id_2;
    }
}

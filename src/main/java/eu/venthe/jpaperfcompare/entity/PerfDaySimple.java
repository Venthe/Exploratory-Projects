package eu.venthe.jpaperfcompare.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfDaySimple {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private String customerNumber;
    private boolean active;

    public boolean getActive() {
        log.trace("");
        return active;
    }
}

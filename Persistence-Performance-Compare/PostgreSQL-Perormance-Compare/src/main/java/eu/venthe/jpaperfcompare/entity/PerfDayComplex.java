package eu.venthe.jpaperfcompare.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfDayComplex {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private String customerNumber;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "perfDayComplex"
    )
    private Set<Period> periods = new HashSet<>();

    public void updateReferences() {
        periods.forEach(period -> period.setPerfDayComplex(this));
    }

    public boolean isWithinPeriod(LocalDate date) {
        log.trace("");
        return periods.parallelStream().anyMatch(period -> period.isWithin(date));
    }
}

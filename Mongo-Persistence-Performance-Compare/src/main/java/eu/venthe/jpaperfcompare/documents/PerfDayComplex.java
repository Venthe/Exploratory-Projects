package eu.venthe.jpaperfcompare.documents;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@ToString
@Builder
@Slf4j
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfDayComplex {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private String customerNumber;

    private Set<Period> periods = new HashSet<>();

    @PersistenceConstructor
    protected PerfDayComplex(Long id, String customerNumber, Set<Period> periods) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.periods = periods;
        updateReferences();
    }

    public void updateReferences() {
        this.periods.forEach(p -> p.setParent(this));
    }

    public boolean isWithinPeriod(LocalDate date) {
        log.trace("");
        return periods.parallelStream().anyMatch(period -> period.isWithin(date));
    }
}

package eu.venthe.jpaperfcompare.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Embeddable
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Period {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perf_day_complex_id", nullable = false)
    @Setter
    @ToString.Exclude
    private PerfDayComplex perfDayComplex;
    private LocalDate startDate;
    private LocalDate endDate;

    @Transient
    public boolean isWithin(LocalDate date) {
        if (startDate == null && endDate == null) {
            return true;
        }
        if (startDate == null && isWithinLowerBound(date)) {
            return true;
        }
        if (endDate == null && isWithinUpperBound(date)) {
            return true;
        }

        return isWithinUpperBound(date) && isWithinLowerBound(date);
    }

    private boolean isWithinUpperBound(LocalDate date) {
        return startDate.equals(date) || date.isAfter(startDate);
    }

    private boolean isWithinLowerBound(LocalDate date) {
        return endDate.equals(date) || date.isBefore(endDate);
    }
}

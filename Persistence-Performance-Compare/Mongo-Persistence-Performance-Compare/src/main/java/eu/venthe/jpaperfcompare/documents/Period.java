package eu.venthe.jpaperfcompare.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Period {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @Transient
    @Setter
    @ToString.Exclude
    PerfDayComplex parent;

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

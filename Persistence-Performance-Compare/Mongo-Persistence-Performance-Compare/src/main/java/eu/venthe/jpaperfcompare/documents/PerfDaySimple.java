package eu.venthe.jpaperfcompare.documents;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
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

package eu.venthe.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
class Reservation {
    @Id
    private String id;
    private String name;

    public static Reservation of(String name) {
        return new Reservation(null, name);
    }
}

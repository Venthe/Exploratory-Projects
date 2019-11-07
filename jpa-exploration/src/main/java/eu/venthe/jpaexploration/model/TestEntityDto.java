package eu.venthe.jpaexploration.model;

import java.time.LocalDateTime;

public interface TestEntityDto {
    long getId();

    String getCharacter();

    Long getNumeric();

    LocalDateTime getDatetime();
}

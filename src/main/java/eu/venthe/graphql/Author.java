package eu.venthe.graphql;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Author {
    String id;
    String name;
    String thumbnail;
}

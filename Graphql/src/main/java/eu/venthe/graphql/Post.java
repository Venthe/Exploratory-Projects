package eu.venthe.graphql;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Post {
    String id;
    String title;
    String text;
    String category;
    String authorId;
}

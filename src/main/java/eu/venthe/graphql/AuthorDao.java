package eu.venthe.graphql;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDao {
    List<Author> REPOSITORY = new ArrayList<>();

    @PostConstruct
    void postConstruct() {
        for (int authorId = 0; authorId < 10; ++authorId) {
            Author author = Author.builder()
                    .id("Author" + authorId)
                    .name("Author " + authorId)
                    .thumbnail("http://example.com/authors/" + authorId)
                    .build();
            REPOSITORY.add(author);
        }
    }

    public Optional<Author> getAuthorById(String authorId) {
        return REPOSITORY.stream()
                .filter(a -> a.getId()
                        .equals(authorId))
                .findFirst();
    }
}

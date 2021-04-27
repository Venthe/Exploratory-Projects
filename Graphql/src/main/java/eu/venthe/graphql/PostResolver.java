package eu.venthe.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostResolver implements GraphQLResolver<Post> {
    private final AuthorDao authorDao;

    public Author getAuthor(Post post) {
        return authorDao.getAuthorById(post.getAuthorId())
                .orElseThrow();
    }
}
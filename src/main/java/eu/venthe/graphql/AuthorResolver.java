package eu.venthe.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorResolver implements GraphQLResolver<Author> {
    private final PostDao postDao;

    public List<Post> getPosts(Author author) {
        return postDao.getAuthorPosts(author.getId());
    }
}

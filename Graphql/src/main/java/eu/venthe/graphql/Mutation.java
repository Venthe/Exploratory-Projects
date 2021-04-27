package eu.venthe.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Service;

@Service
public class Mutation implements GraphQLMutationResolver {
    private PostDao postDao;

    public Post writePost(String title, String text, String category) {
        return postDao.savePost(title, text, category);
    }
}
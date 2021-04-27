package eu.venthe.graphql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostDao {
    static List<Post> POSTS = new ArrayList<>();

    static {
        for (int postId = 0; postId < 10; ++postId) {
            for (int authorId = 0; authorId < 10; ++authorId) {
                Post post = Post.builder()
                        .id("Post" + authorId + postId)
                        .title("Post " + authorId + ":" + postId)
                        .text("Post " + postId + " + by author " + authorId)
                        .id("Author" + authorId)
                        .build();
                POSTS.add(post);
            }
        }
    }

    public List<Post> getRecentPosts(int count, int offset) {
        log.info("getRecentPosts. {} {}", count, offset);
        return POSTS.stream().skip(offset).limit(count).collect(Collectors.toList());
    }

    public Post savePost(String title, String text, String category) {
        log.info("savePost. {} {} {}", title, text, category);
        Post e = Post.builder()
                .title(title)
                .text(text)
                .category(category)
                .id(UUID.randomUUID().toString())
                .build();
        POSTS.add(e);
        return e;
    }

    public List<Post> getAuthorPosts(String id) {
        return POSTS.stream().filter(post -> post.getAuthorId().equals(id)).collect(Collectors.toList());

    }
}

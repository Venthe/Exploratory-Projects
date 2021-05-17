package eu.venthe.mutation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutationExampleTest {

    @Test
    void test1() {
        MutationExample mutationExample = new MutationExample();
        int test = mutationExample.test(true);
        Assertions.assertThat(test).isEqualTo(1);
    }

    @Test
    void test2() {
        MutationExample mutationExample = new MutationExample();
        int test = mutationExample.test(false);
        Assertions.assertThat(test).isEqualTo(2);
    }
}
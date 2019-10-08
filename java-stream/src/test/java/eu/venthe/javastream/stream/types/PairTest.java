package eu.venthe.javastream.stream.types;

import org.junit.Test;

public class PairTest {
    private final static String FIRST_VALUE = "first";
    private final static String SECOND_VALUE = "second";

    @Test
    public void pairIsCreatedProperlyWithLeftValue() {
        // given & when
        Pair<String, Object> pair = Pair.of(FIRST_VALUE, null);

        // then
        PairAssert.assertThat(pair)
                .firstValueEquals(FIRST_VALUE)
                .secondValueEquals(null);
    }

    @Test
    public void pairIsCreatedProperlyWithRightValue() {
        // given & when
        Pair<Object, String> pair = Pair.of(null, SECOND_VALUE);

        // then
        PairAssert.assertThat(pair)
                .firstValueEquals(null)
                .secondValueEquals(SECOND_VALUE);
    }

    @Test
    public void pairIsCreatedProperlyWithNeitherValues() {
        // given & when
        Pair<Object, Object> pair = Pair.of(null, null);

        // then
        PairAssert.assertThat(pair)
                .firstValueEquals(null)
                .secondValueEquals(null);
    }

    @Test
    public void pairIsCreatedProperlyWithBoth() {
        // given & when
        Pair<String, String> pair = Pair.of(FIRST_VALUE, SECOND_VALUE);

        // then
        PairAssert.assertThat(pair)
                .firstValueEquals(FIRST_VALUE)
                .secondValueEquals(SECOND_VALUE);
    }
}
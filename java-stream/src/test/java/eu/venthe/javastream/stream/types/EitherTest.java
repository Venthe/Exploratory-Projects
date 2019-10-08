package eu.venthe.javastream.stream.types;

import org.junit.Test;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EitherTest {
    private final static String NULL_CONSTRUCTION_ERROR = "Either cannot be empty at left and right side at the same time";

    @Test
    public void whenConstructedWithNullThrowsError() {
        // given & when
        Throwable result1 = catchThrowable(() -> Either.left(null));
        Throwable result2 = catchThrowable(() -> Either.right(null));

        // then
        assertThat(result1).hasMessage(NULL_CONSTRUCTION_ERROR)
                .isExactlyInstanceOf(InvalidParameterException.class);
        assertThat(result2).hasMessage(NULL_CONSTRUCTION_ERROR)
                .isExactlyInstanceOf(InvalidParameterException.class);
    }

}

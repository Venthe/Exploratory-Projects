package eu.venthe.javastream.stream.types;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

@SuppressWarnings("ALL")
class PairAssert extends AbstractAssert<PairAssert, Pair> {

    PairAssert(Pair actual) {
        super(actual, PairAssert.class);
    }

    static PairAssert assertThat(Pair actual) {
        return new PairAssert(actual);
    }

    <T> PairAssert firstValueEquals(T value) {
        isNotNull();

        // check condition
        if (!Objects.equals(actual.getFirst(), value)) {
            failWithMessage("Expected first value to be <%s> but was <%s>", value, actual.getFirst());
        }

        // return the current assertion for method chaining
        return this;
    }

    <T> PairAssert secondValueEquals(T value) {
        isNotNull();

        // check condition
        if (!Objects.equals(actual.getSecond(), value)) {
            failWithMessage("Expected first value to be <%s> but was <%s>", value, actual.getSecond());
        }

        // return the current assertion for method chaining
        return this;
    }

}
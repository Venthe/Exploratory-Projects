package eu.venthe.javastream.stream.types;


import eu.venthe.javastream.stream.CheckedFunction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("WeakerAccess")
public final class Either<Left, Right> {
    protected final Left left;
    protected final Right right;

    public Optional<Left> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<Right> getRight() {
        return Optional.ofNullable(right);
    }

    /**
     * Checks if left value is defined.
     */
    public boolean isLeft() {
        return left != null;
    }

    /**
     * Checks if right value is defined.
     */
    public boolean isRight() {
        return right != null;
    }

    /**
     * Checks if neither value is defined.
     */
    public boolean isEmpty() {
        return !isRight() && !isLeft();
    }

    /**
     * Reduces left value.
     */
    public <Output> Optional<Output> reduceLeft(Function<? super Left, Output> mapper) {
        return getLeft().map(mapper);
    }

    /**
     * Maps left value.
     */
    public <Output> Either<Output, Right> mapLeft(Function<? super Left, Output> mapper) {
        if (left == null) {
            throw new IllegalStateException("You cannot map left when left is null");
        }

        return Either.of(toNull(reduceLeft(mapper)), null);
    }

    /**
     * Reduces right value.
     */
    public <Output> Optional<Output> reduceRight(Function<? super Right, Output> mapper) {
        return getRight().map(mapper);
    }

    /**
     * Maps right value.
     */
    public <Output> Either<Left, Output> mapRight(Function<? super Right, Output> mapper) {
        if (right == null) {
            throw new IllegalStateException("You cannot map right when right is null");
        }

        return Either.of(null, toNull(reduceRight(mapper)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private <Type> Type toNull(Optional<Type> optionalType) {
        return optionalType.orElse(null);
    }

    /**
     * Creates Either with only left value.
     */
    public static <Left, Right> Either<Left, Right> left(Left value) {
        return of(value, null);
    }

    /**
     * Creates Either with only right value.
     */
    public static <Left, Right> Either<Left, Right> right(Right value) {
        return of(null, value);
    }

    /**
     * Creates Either with both values.
     */
    private static <Left, Right> Either<Left, Right> of(Left left, Right right) {
        if (left != null && right != null) {
            throw new InvalidParameterException(MessageFormat.format("Either cannot be created with Left={0} and Right={1} side at the same time", left, right));
        }
        if (left == null && right == null) {
            throw new InvalidParameterException("Either cannot be empty at left and right side at the same time");
        }

        return new Either<>(left, right);
    }

    /**
     * Creates Either with right value as result of function and left value as exception.
     */
    public static <Input, CorrectValue> Function<Input, Either<Exception, CorrectValue>> lift(CheckedFunction<Input, CorrectValue> function) {
        return lift(function, Either::dropRight);
    }

    private static <Left, Right> Left dropRight(Left left, Right right) {
        return left;
    }

    /**
     * Creates Either with right value as result of function and left value as exception-value pair.
     */
    public static <Input, CorrectResult> Function<Input, Either<Pair<Exception, Input>, CorrectResult>> liftWithValue(CheckedFunction<Input, CorrectResult> function) {
        return lift(function, Pair::of);
    }

    /**
     * Creates Either with right value as result of function and left value as a mapped exception-value pair.
     */
    public static <Input, CorrectResult, ExceptionResult> Function<Input, Either<ExceptionResult, CorrectResult>> lift(CheckedFunction<Input,
            CorrectResult> function, BiFunction<Exception, Input, ExceptionResult> consumer) {
        return (Input input) -> {
            try {
                return Either.right(function.apply(input));
            } catch (Exception exception) {
                return Either.left(consumer.apply(exception, input));
            }
        };
    }

    /**
     * Consumes Either.
     */
    public static <Left, Right> Consumer<Either<Left, Right>> consume(
            Consumer<Left> leftConsumer, Consumer<Right> rightConsumer) {
        return (Either<Left, Right> either) -> {
            either.getRight().ifPresent(rightConsumer);
            either.getLeft().ifPresent(leftConsumer);
        };
    }

    @Override
    public String toString() {
        return Stream.of(
                getLeft().map(left -> MessageFormat.format("Left={0}", left)),
                getRight().map(right -> MessageFormat.format("Right={0}", right))
        )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining(", "));
    }
}

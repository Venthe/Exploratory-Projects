package eu.venthe.javastream.stream;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<Input, Output> {
    Output apply(Input type) throws Exception;

    public static <Input, Output> Function<Input, Output> wrap(CheckedFunction<Input, Output> checkedFunction) {
        return (Input value) -> {
            try {
                return checkedFunction.apply(value);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        };
    }
}
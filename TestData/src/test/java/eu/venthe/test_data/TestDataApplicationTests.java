package eu.venthe.test_data;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Slf4j
class TestDataApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(rec(new Faker(), "[{0}] {1}", "\n"));
    }

    private String rec(Object object, String mf, String delim) {
        return Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(isPrivate().negate())
                .filter(noParameters())
                .filter(method("hashCode")
                        .or(method("toString"))
                        .or(method("byteValue"))
                        .or(method("random"))
                        .or(method("instance"))
                        .or(method("clone"))
                        .negate()
                )
                .map(field -> {
                    Optional<Object> invokeO = invocation(object, field);
                    if (invokeO.isEmpty()) return Optional.<Pair<String, Object>>empty();
                    Object invoke = invokeO.get();
                    if (invoke instanceof Faker) return Optional.<Pair<String, Object>>empty();
                    if (invoke instanceof Integer) return Optional.<Pair<String, Object>>empty();
                    if (invoke instanceof Long) return Optional.<Pair<String, Object>>empty();
                    if (invoke instanceof Double) return Optional.<Pair<String, Object>>empty();
                    if (invoke instanceof Date) return of(Pair.of(field.getName(), invoke.toString()));
                    if (invoke instanceof String) return of(Pair.of(field.getName(), invoke.toString()));
                    if (invoke instanceof Boolean) return of(Pair.of(field.getName(), invoke.toString()));
                    if (invoke instanceof Character) return of(Pair.of(field.getName(), invoke.toString()));
                    if (invoke instanceof List)
                        return of(Pair.of(
                                field.getName(),
                                ((List<?>) invoke).stream()
                                        .map(Object::toString)
                                        .collect(Collectors.joining(", "))
                        ));
                    return of(Pair.of(field.getName(), rec(invoke, "\n\t[{0}: {1}]", "")));

                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(a -> MessageFormat.format(mf, a.getKey(), a.getValue()))
                .collect(Collectors.joining(delim));
    }

    private Predicate<Method> method(String hashCode) {
        return a -> a.getName().equals(hashCode);
    }

    private Optional<Object> invocation(Object object, Method field) {
        try {
            return of(field.invoke(object));
        } catch (IllegalAccessException | InvocationTargetException e) {
            return empty();
        }
    }

    private Predicate<Method> noParameters() {
        return b -> b.getParameterCount() == 0;
    }

    private Predicate<Method> isPrivate() {
        return b -> Modifier.isPrivate(b.getModifiers());
    }
}

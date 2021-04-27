package com.example.demo.cascade;

import java.lang.reflect.Field;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import static org.springframework.util.ReflectionUtils.*;

@Getter
@Setter
@AllArgsConstructor
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        makeAccessible(field);

        if (!field.isAnnotationPresent(DBRef.class) || !field.isAnnotationPresent(CascadeSave.class)) {
            return;
        }

        final Object fieldValue = field.get(getSource());

        if (fieldValue == null) {
            return;
        }

        doWithFields(fieldValue.getClass(), ReflectionUtils::makeAccessible);
        if (fieldValue instanceof Iterable) {
            ((Iterable<?>) fieldValue).forEach(this::saveDocument);
        } else if (fieldValue instanceof Map) {
            ((Map<?, ?>) fieldValue).values().forEach(this::saveDocument);
        } else {
            saveDocument(fieldValue);
        }
    }

    private void saveDocument(Object fieldValue) {
        if (!DocumentCallback.findId(fieldValue)) {
            throw new UnsupportedOperationException("Cannot lazily persist document which has no ID");
        }

        getMongoOperations().save(fieldValue);
    }
}

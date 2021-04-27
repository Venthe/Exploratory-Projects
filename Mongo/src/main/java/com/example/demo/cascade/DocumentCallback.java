package com.example.demo.cascade;

import java.lang.reflect.Field;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import static org.springframework.util.ReflectionUtils.doWithFields;

public class DocumentCallback implements ReflectionUtils.FieldCallback {
    @Getter
    private boolean idFound;

    public static boolean findId(Object fieldValue) {
        final DocumentCallback documentCallback = new DocumentCallback();
        doWithFields(fieldValue.getClass(), documentCallback);
        return documentCallback.isIdFound();
    }

    @Override
    public void doWith(final Field field) throws IllegalArgumentException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(Id.class)) {
            idFound = true;
        }
    }
}

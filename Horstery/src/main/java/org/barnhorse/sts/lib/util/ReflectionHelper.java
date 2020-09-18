package org.barnhorse.sts.lib.util;

import java.lang.reflect.Field;
import java.util.Optional;

public class ReflectionHelper {

    public static <T> T getFieldValue(Object instance, String fieldName, boolean setAccessible)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        if (setAccessible) {
            field.setAccessible(true);
        }
        return (T) field.get(instance);
    }

    public static <T> Optional<T> tryGetFieldValue(Object instance, String fieldName, boolean setAccessible) {
        try {
            return Optional.of(getFieldValue(instance, fieldName, setAccessible));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

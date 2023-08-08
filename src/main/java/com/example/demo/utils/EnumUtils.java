package com.example.demo.utils;

import java.lang.reflect.InvocationTargetException;

public class EnumUtils {

    /**
     * This method converts a class type and value to an enum, currently, it is being used in
     * com.dti.oze.specifications.QueryToCriteria
     *
     * @param classType the target class type
     * @param value     the value to be converted the to target class
     * @return Object | value if any failure occurs
     */
    public static Object toEnum(Class<?> classType, Object value) {
        if (classType.isEnum()) {
            try {
                return classType.getMethod("valueOf", String.class)
                        .invoke(new Object(), String.valueOf(value));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
        }
        return value;
    }
}
